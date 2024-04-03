package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.entity.Shapes;
import sk.tuke.gamestudio.entity.Tiles;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;
import sk.tuke.gamestudio.game.block_puzzle.core.ShapeTile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class LevelServiceJPA implements LevelService{
    @PersistenceContext
    private EntityManager entityManager;
    private Level level;

    @Override
    public Level getLevel(int levelID, Field field) throws GameStudioException {
        level = entityManager.createNamedQuery("Level.getLevel", Level.class)
                .setParameter("level_id", levelID)
                .getSingleResult();

        level.setField(field);
        generateShapes();
        return level;
    }

    private void generateShapes() throws GameStudioException {
        List<Shapes> shapes = entityManager.createNamedQuery("Shapes.getShapes", Shapes.class)
                .setParameter("level_id", level.getIdent())
                .getResultList();

        for (Shapes shapeList : shapes) {
            placeShapeNumberOnField(shapeList.getShapeNumber());

            Shape shape = new Shape(shapeList.getShapeColor());
            shape.setField(level.getField());
            shape.setShapeWidth(shapeList.getShapeWidth());
            shape.setShapeHeight(shapeList.getShapeHeight());

            generateTiles(shape);
            level.getShapes().add(shape);
        }
    }

    private void placeShapeNumberOnField(int shapeNumber) {
        int posX = 24;
        int posY = 2;
        switch (shapeNumber) {
            case 2:
                posY = 5;
                break;
            case 3:
                posY = 8;
                break;
            case 4:
                posX = 38;
                break;
            case 5:
                posX = 38;
                posY = level.getShapeCount() <= 5 ? 8 : 5;
                break;
            case 6:
                posX = 38;
                posY = 8;
                break;
        }
        level.getField().getMap()[posX][posY].setValue("(");
        level.getField().getMap()[posX+1][posY].setValue(String.valueOf(shapeNumber));
        level.getField().getMap()[posX+2][posY].setValue(")");
    }

    private void generateTiles(Shape shape) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        List<Tiles> tilesList = entityManager.createNamedQuery("Tiles.getTiles", Tiles.class)
                .getResultList();

        for (Tiles tile : tilesList) {
            shape.getShape().add(new ShapeTile(tile.getPosX(), tile.getPosY()));

            minX = Math.min(tile.getPosX(), minX);
            minY = Math.min(tile.getPosY(), minY);
        }
        shape.getCoordinates().setFirstMinX(minX);
        shape.getCoordinates().setFirstMinY(minY);
    }
}
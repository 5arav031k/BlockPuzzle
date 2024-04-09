package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.entity.Shapes;
import sk.tuke.gamestudio.entity.Tiles;
import sk.tuke.gamestudio.game.block_puzzle.core.Color;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;
import sk.tuke.gamestudio.game.block_puzzle.core.ShapeTile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class LevelServiceJPA implements LevelService {

    @PersistenceContext
    private EntityManager entityManager;
    private Level level;

    @Override
    public Level getLevel(int levelID) throws GameStudioException {
        if (levelID < 0 || levelID > 6)
            throw new GameStudioException("Invalid level ID");

        level = entityManager.createNamedQuery("Level.getLevel", Level.class)
                .setParameter("level_id", levelID)
                .getSingleResult();

        generateShapes();
        return level;
    }

    private void generateShapes() throws GameStudioException {
        List<Shapes> shapes = entityManager.createNativeQuery("SELECT * FROM shapes WHERE level_id = :level_id ORDER BY shape_id", Shapes.class)
                .setParameter("level_id", level.getIdent())
                .getResultList();

        if (shapes == null || shapes.isEmpty())
            throw new GameStudioException("No shapes found");

        for (Shapes shapeList : shapes) {
            Shape shape = new Shape(Color.getColorByString(shapeList.getShapeColor()), shapeList.getShapeNumber(), shapeList.getShapeWidth(), shapeList.getShapeHeight());
            generateTiles(shape, shapeList.getIdent());
            level.getShapes().add(shape);
        }
    }

    private void generateTiles(Shape shape, int shape_id) throws GameStudioException{
        if (shape == null || shape.getShapeNumber() < 0 || shape.getShapeNumber() > 6 || shape_id == 0)
            throw new GameStudioException("Invalid shape or shape ID");

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        List<Tiles> tilesList = entityManager.createNativeQuery(
                "SELECT * FROM tiles t JOIN shapes_tiles st on t.tile_id = st.tile_id " +
                        "WHERE st.shape_id = :shape_id ORDER BY t.tile_id", Tiles.class)
                .setParameter("shape_id", shape_id)
                .getResultList();

        if (tilesList == null || tilesList.isEmpty())
            throw new GameStudioException("No tiles found");

        for (Tiles tile : tilesList) {
            shape.getShape().add(new ShapeTile(tile.getPosX(), tile.getPosY()));

            minX = Math.min(tile.getPosX(), minX);
            minY = Math.min(tile.getPosY(), minY);
        }
        shape.getCoordinates().setFirstMinX(minX);
        shape.getCoordinates().setFirstMinY(minY);
    }
}
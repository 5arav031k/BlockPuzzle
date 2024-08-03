package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.game.block_puzzle.core.Color;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;
import sk.tuke.gamestudio.game.block_puzzle.core.ShapeTile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelServiceJDBC implements LevelService {
    private Level level;
    private final Connection connection = DBInitializer.getConnection();

    @Override
    public Level getLevel(int level_id, Field field) {
        level = new Level(field);
        String GET_SHAPES_COUNT = "SELECT shapes_count FROM levels WHERE level_id = ?";
        String GET_SHAPES = "SELECT * FROM shapes WHERE level_id = ? ORDER BY shape_id";
        try {
            var statement = connection.prepareStatement(GET_SHAPES_COUNT);
            statement.setInt(1, level_id);
            ResultSet levels_rs = statement.executeQuery();
            if (!levels_rs.next()) return null;

            level.setShapeCount(levels_rs.getInt(1));

            statement = connection.prepareStatement(GET_SHAPES);
            statement.setInt(1, level_id);
            ResultSet shapes_rs = statement.executeQuery();

            generateShapes(connection, shapes_rs);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return level;
    }

    @Override
    public void generateShapes(Connection connection, ResultSet shapes_rs) {
        try {
            while (shapes_rs.next()) {
                int current_shape_id = shapes_rs.getInt("shape_id");
                int shapeNumber = shapes_rs.getInt("shape_number");
                String color = shapes_rs.getString("shape_color");

                placeShapeNumberOnField(shapeNumber);

                Shape shape = new Shape(Color.getColorByString(color));
                shape.setField(level.getField());
                shape.setShapeWidth(shapes_rs.getInt("shape_width"));
                shape.setShapeHeight(shapes_rs.getInt("shape_height"));

                generateTiles(connection, shape, current_shape_id);
                level.getShapes().add(shape);
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
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
        level.getField().getMap()[posX + 1][posY].setValue(String.valueOf(shapeNumber));
        level.getField().getMap()[posX + 2][posY].setValue(")");
    }

    private void generateTiles(Connection connection, Shape shape, int shape_id) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        String GET_TILE_ID = "SELECT tile_id FROM shapes_tiles WHERE shape_id = ?";
        String GET_TILE = "SELECT tile_pos_x, tile_pos_y FROM tiles WHERE tile_id = ? ORDER BY tile_id";
        try {
            var statement = connection.prepareStatement(GET_TILE_ID);
            statement.setInt(1, shape_id);
            ResultSet shapes_tiles_rs = statement.executeQuery();

            while (shapes_tiles_rs.next()) {
                int current_tile_id = shapes_tiles_rs.getInt(1);

                statement = connection.prepareStatement(GET_TILE);
                statement.setInt(1, current_tile_id);
                ResultSet tiles_rs = statement.executeQuery();
                if (!tiles_rs.next()) return;

                int posX = tiles_rs.getInt(1);
                int posY = tiles_rs.getInt(2);
                shape.getShape().add(new ShapeTile(posX, posY));

                minX = Math.min(posX, minX);
                minY = Math.min(posY, minY);
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        shape.getCoordinates().setFirstMinX(minX);
        shape.getCoordinates().setFirstMinY(minY);
    }
}

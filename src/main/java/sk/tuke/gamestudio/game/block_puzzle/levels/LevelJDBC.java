package main.java.sk.tuke.gamestudio.game.block_puzzle.levels;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Color;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Shape;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.ShapeTile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LevelJDBC implements Level{
    private final List<Shape> shapes;
    private final Field field;
    private int shapeCount;

    public LevelJDBC(Field field) {
        this.field = field;
        shapes = new ArrayList<>();
    }

    @Override
    public int getShapeCount() {
        return shapeCount;
    }
    @Override
    public void setShapesCount(int shapeCount) {
        this.shapeCount = shapeCount;
    }
    @Override
    public List<Shape> getShapes() {
        return shapes;
    }
    @Override
    public void generateShapesJDBC(Connection connection, ResultSet shapes_rs) throws SQLException {
        while (shapes_rs.next()) {
            int current_shape_id = shapes_rs.getInt("shape_id");
            int shapeNumber = shapes_rs.getInt("shape_number");
            String color = shapes_rs.getString("shape_color");

            placeShapeNumberOnField(shapeNumber);

            Shape shape = new Shape(Color.getColorByString(color));
            shape.setField(field);
            shape.setShapeWidth(shapes_rs.getInt("shape_width"));
            shape.setShapeHeight(shapes_rs.getInt("shape_height"));

            generateTilesJDBC(connection, shape, current_shape_id);
            shapes.add(shape);
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
                posY = shapeCount <= 5 ? 8 : 5;
                break;
            case 6:
                posX = 38;
                posY = 8;
                break;
        }
        field.getMap()[posX][posY].setValue("(");
        field.getMap()[posX+1][posY].setValue(String.valueOf(shapeNumber));
        field.getMap()[posX+2][posY].setValue(")");
    }
    private void generateTilesJDBC(Connection connection, Shape shape, int shape_id) throws SQLException{
        String GET_TILE_ID = "SELECT tile_id FROM shapes_tiles WHERE shape_id = "+shape_id;
        ResultSet shapes_tiles_rs = connection.prepareStatement(GET_TILE_ID).executeQuery();

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        while (shapes_tiles_rs.next()) {
            int current_tile_id = shapes_tiles_rs.getInt(1);

            String GET_TILE = "SELECT tile_pos_x, tile_pos_y FROM tiles WHERE tile_id = "+current_tile_id+" ORDER BY tile_id";
            ResultSet tiles_rs = connection.prepareStatement(GET_TILE).executeQuery();
            if (!tiles_rs.next()) return;

            int posX = tiles_rs.getInt(1);
            int posY = tiles_rs.getInt(2);
            shape.getShape().add(new ShapeTile(posX, posY));

            minX = Math.min(posX, minX);
            minY = Math.min(posY, minY);
        }
        shape.getCoordinates().setFirstMinX(minX);
        shape.getCoordinates().setFirstMinY(minY);
    }
}

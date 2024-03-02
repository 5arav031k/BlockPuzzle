package main.java.sk.tuke.gamestudio.game.block_puzzle.levels;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Color;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Shape;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Tile;

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
    public void setShapeCount(int shapeCount) {
        this.shapeCount = shapeCount;
    }
    @Override
    public List<Shape> getShapes() {
        return shapes;
    }
    @Override
    public void generateShapesJDBC(Connection connection, ResultSet shapes_rs) throws SQLException {
        if (!shapes_rs.next()) return;

        for (int i = 2; i <= shapeCount+1; i++) {
            int posX = shapes_rs.getInt(i);
            int posY = shapes_rs.getInt(i+7);
            field.getMap()[posX][posY] = "(";
            field.getMap()[posX+1][posY] = String.valueOf(i-1);
            field.getMap()[posX+2][posY] = ")";
        }

        for (int i = 16; i < 16+shapeCount; i++) {
            Shape shape = new Shape(Color.getColorByString(shapes_rs.getString(i)));
            shape.setField(field);
            shape.setShapeWidth(shapes_rs.getInt(i+14));
            shape.setShapeHeight(shapes_rs.getInt(i+21));

            generateTiles(connection, shape, shapes_rs.getInt(i+7));
            shapes.add(shape);
        }
    }
    private void generateTiles(Connection connection, Shape shape, int id) throws SQLException{
        String SELECT_TILE = "SELECT * FROM tile_set WHERE tile_set_id = " + id;
        var statement = connection.prepareStatement(SELECT_TILE);
        ResultSet tile_rs = statement.executeQuery();
        if (!tile_rs.next()) return;

        for (int j = 3; j < tile_rs.getInt(2)+3; j++) {
            shape.getShape().add(new Tile(tile_rs.getInt(j), tile_rs.getInt(j+10)));
        }

        shape.getCoordinates().setFirstMinX(tile_rs.getInt(23));
        shape.getCoordinates().setFirstMinY(tile_rs.getInt(24));
    }
}

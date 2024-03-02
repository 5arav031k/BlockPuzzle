package main.java.sk.tuke.gamestudio.game.block_puzzle.levels;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Level {
    void setShapeCount(int shapeCount);
    int getShapeCount();
    List<Shape> getShapes();
    void generateShapesJDBC(Connection connection, ResultSet shapes_rs) throws SQLException;
}

package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.LevelJDBC;

import java.sql.*;

public class LevelServiceJDBC implements LevelService {
    private final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private final String USER = "postgres";
    private final String PASSWORD = "}bI1;8s=O,";

    @Override
    public Level getLevelJDBC(int levelID, Field field) {
        Level level = new LevelJDBC(field);
        String SELECT_LEVEL = "SELECT level_id, shapes_count, shapes_id FROM levels WHERE level_id = "+levelID;
        String SELECT_SHAPES = "SELECT * FROM shapes WHERE shapes_id = ";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            PreparedStatement statement = connection.prepareStatement(SELECT_LEVEL);
            ResultSet levels_rs = statement.executeQuery();

            if (!levels_rs.next()) return null;

            level.setShapeCount(levels_rs.getInt(2));
            int shapeID = levels_rs.getInt(3);

            statement = connection.prepareStatement(SELECT_SHAPES + shapeID);
            ResultSet shapes_rs = statement.executeQuery();
            level.generateShapesJDBC(connection, shapes_rs);

        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
        return level;
    }
}

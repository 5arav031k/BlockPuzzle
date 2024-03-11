package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.LevelJDBC;

import java.sql.*;

public class LevelServiceJDBC extends Service implements LevelService {
    @Override
    public Level getLevelJDBC(int level_id, Field field) {;
        Level level = new LevelJDBC(field);
        String GET_SHAPES_COUNT = "SELECT shapes_count FROM levels WHERE level_id = "+level_id;
        String GET_SHAPES = "SELECT * FROM shapes WHERE level_id = "+level_id+" ORDER BY shape_id";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            ResultSet levels_rs = connection.prepareStatement(GET_SHAPES_COUNT).executeQuery();
            if (!levels_rs.next()) return null;

            level.setShapesCount(levels_rs.getInt(1));

            ResultSet shapes_rs = connection.prepareStatement(GET_SHAPES).executeQuery();
            level.generateShapesJDBC(connection, shapes_rs);
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
        return level;
    }
}

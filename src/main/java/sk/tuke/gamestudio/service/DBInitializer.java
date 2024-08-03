package sk.tuke.gamestudio.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInitializer {
    private static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private static final String USER = "postgres";
    private static final String PASSWORD = "}bI1;8s=O,";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return connection;
    }
}

package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentServiceJDBC extends Service implements CommentService{
    @Override
    public void addComment(User user, String comment) {
        String ADD_COMMENT = "INSERT INTO comment (login, comment) VALUES (?, ?) ON CONFLICT (login) DO UPDATE SET comment = EXCLUDED.comment";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_COMMENT))
        {
            statement.setString(1, user.login());
            statement.setString(2, comment);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }

    @Override
    public void reset() {
        String RESET = "DELETE FROM comment";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(RESET))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }
}

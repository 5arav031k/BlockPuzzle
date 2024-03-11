package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingServiceJDBC extends Service implements RatingService{
    @Override
    public void addRating(User user, int rating) {
        String ADD_RATING = "INSERT INTO rating (login, rating) VALUES (?, ?) ON CONFLICT (login) DO UPDATE SET rating = EXCLUDED.rating";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_RATING))
        {
            statement.setString(1, user.login());
            statement.setInt(2, rating);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }

    @Override
    public void reset() {
        String RESET = "DELETE FROM rating";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(RESET))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }
}

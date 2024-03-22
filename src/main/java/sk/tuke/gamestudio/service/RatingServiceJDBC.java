package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.User;

import java.sql.*;

public class RatingServiceJDBC extends Service implements RatingService{
    @Override
    public void setRating(Rating rating) {
        String ADD_RATING = "INSERT INTO rating (login, rating, rated_on) VALUES (?, ?, ?) ON CONFLICT (login) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_RATING))
        {
            statement.setString(1, rating.getLogin());
            statement.setInt(2, rating.getRating());
            statement.setTimestamp(3, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating() {
        String GET_AVERAGE_RATING = "SELECT ROUND(AVG(rating)) AS average_rating FROM rating";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_RATING);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next())  return rs.getInt(1);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return 0;
    }

    @Override
    public int getRating(User user) {
        String GET_RATING = String.format("SELECT rating from rating WHERE login = '%s'", user.getLogin());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_RATING);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next())  return rs.getInt(1);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return 0;
    }

    @Override
    public void reset() {
        String RESET = "DELETE FROM rating";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(RESET))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

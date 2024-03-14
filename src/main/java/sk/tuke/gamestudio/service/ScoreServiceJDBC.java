package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreServiceJDBC extends Service implements ScoreService {

    @Override
    public void addCompletedLevel(Score score, int level) {
        if (level <= score.levelsCompleted())
            return;
        String ADD_LEVEL = "UPDATE score SET levels_completed = ?, completed_at = ? WHERE login = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_LEVEL))
        {
           statement.setInt(1, level);
           statement.setTimestamp(2, new Timestamp(new Date().getTime()));
           statement.setString(3, score.login());
           statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public Score addScore(User user) {
        if (user == null)   return null;

        String ADD_SCORE = "INSERT INTO score VALUES (?, DEFAULT, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_SCORE))
        {
            Date date = new Date();
            statement.setString(1, user.login());
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.executeUpdate();
            return new Score(user.login(), 0, date);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public Score getScore(User user) {
        if (user == null)   return null;

        String GET_SCORE = String.format("SELECT levels_completed, completed_at FROM score WHERE login = '%s'", user.login());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            ResultSet rs = connection.prepareStatement(GET_SCORE).executeQuery();
            if (rs.next())
                return new Score(user.login(), rs.getInt(1), rs.getTimestamp(2));

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return null;
    }

    @Override
    public List<Score> getTopScores() {
        String GET_SCORE = "SELECT * FROM score ORDER BY levels_completed DESC, completed_at LIMIT 5";
        List<Score> scoreList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            ResultSet rs = connection.prepareStatement(GET_SCORE).executeQuery();
            while (rs.next())
                scoreList.add(new Score(rs.getString(1), rs.getInt(2), rs.getTimestamp(3)));

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return scoreList;
    }

    @Override
    public void reset() {
        String RESET = "DELETE FROM score";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(RESET))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {
    private final Connection connection = DBInitializer.getConnection();

    @Override
    public void addCompletedLevel(Score score, int level) {
        if (level <= score.getLevelsCompleted())
            return;

        String ADD_LEVEL = "UPDATE score SET levels_completed = ?, completed_at = ? WHERE login = ?";
        try {
            var statement = connection.prepareStatement(ADD_LEVEL);
            statement.setInt(1, level);
            statement.setTimestamp(2, new Timestamp(new Date().getTime()));
            statement.setString(3, score.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public Score addScore(User user) {
        if (user == null) return null;

        String ADD_SCORE = "INSERT INTO score VALUES (?, DEFAULT, ?)";
        try {
            var statement = connection.prepareStatement(ADD_SCORE);
            Date date = new Date();
            statement.setString(1, user.getLogin());
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.executeUpdate();
            return new Score(user.getLogin(), 0, date);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public Score getScore(User user) {
        if (user == null) return null;

        String GET_SCORE = "SELECT levels_completed, completed_at FROM score WHERE login = ?";
        try {
            var statement = connection.prepareStatement(GET_SCORE);
            statement.setString(1, user.getLogin());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return new Score(user.getLogin(), rs.getInt(1), rs.getTimestamp(2));
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return null;
    }

    @Override
    public List<Score> getTopScores() {
        String GET_SCORE = "SELECT * FROM score ORDER BY levels_completed DESC, completed_at LIMIT 5";
        List<Score> scoreList = new ArrayList<>();
        try {
            ResultSet rs = connection.prepareStatement(GET_SCORE).executeQuery();
            while (rs.next())
                scoreList.add(new Score(rs.getString(1), rs.getInt(2), rs.getTimestamp(3)));
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return scoreList;
    }

    @Override
    public void reset(User user) {
        String RESET = "DELETE FROM score WHERE login = ?";
        try {
            var statement = connection.prepareStatement(RESET);
            statement.setString(1, user.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
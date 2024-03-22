package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC extends Service implements CommentService{
    @Override
    public void addComment(Comment comment) {
        String ADD_COMMENT = "INSERT INTO comment (login, comment, commented_on) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(ADD_COMMENT))
        {
            statement.setString(1, comment.getLogin());
            statement.setString(2, comment.getComment());
            statement.setTimestamp(3, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(User user) {
        List<Comment> comments = new ArrayList<>();
        String GET_COMMENTS = String.format("SELECT * from comment WHERE login = '%s' ORDER BY commented_on DESC", user.getLogin());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(GET_COMMENTS);
             ResultSet rs = statement.executeQuery())
        {
            while (rs.next()) {
                comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getTimestamp(3)));
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public void reset() {
        String RESET = "DELETE FROM comment";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(RESET))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

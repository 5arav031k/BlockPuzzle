package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    private final Connection connection = DBService.getConnection();

    @Override
    public void addComment(Comment comment) {
        if (comment == null || comment.getComment().length() >= 300 || comment.getComment().isEmpty()) {
            throw new GameStudioException("Invalid comment");
        }

        String ADD_COMMENT = "INSERT INTO comment (login, comment, commented_on) VALUES (?, ?, ?)";
        try {
            var statement = connection.prepareStatement(ADD_COMMENT);
            statement.setString(1, comment.getLogin());
            statement.setString(2, comment.getComment());
            statement.setTimestamp(3, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments() {
        String GET_COMMENTS = "SELECT * from comment ORDER BY commented_on DESC LIMIT 7";

        List<Comment> comments = new ArrayList<>();
        try {
            var statement = connection.prepareStatement(GET_COMMENTS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(rs.getString("login"), rs.getString("comment"), rs.getTimestamp("commented_on")));
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return comments;
    }

    @Override
    public void deleteComment(User user) {
        String RESET = "DELETE FROM comment WHERE login = ?";
        try {
            var statement = connection.prepareStatement(RESET);
            statement.setString(1, user.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

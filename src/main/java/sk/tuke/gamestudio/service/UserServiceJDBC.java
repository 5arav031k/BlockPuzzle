package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceJDBC implements UserService {
    private final Connection connection = DBInitializer.getConnection();

    @Override
    public User addUser(String login, String password) {
        String ADD_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?)";
        try {
            var statement = connection.prepareStatement(ADD_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.executeUpdate();
            return new User(login, password);
        } catch (SQLException e) {
            if (e.getErrorCode() == 0)
                System.out.println("           \u001B[31m" + "This login is already taken!" + "\u001B[0m");
            else
                throw new GameStudioException(e);
        }
        return null;
    }

    @Override
    public User logIn(String login, String password) {
        String SELECT_USER = "SELECT login FROM users WHERE login = ? AND password = ?";
        try {
            var statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet users_rs = statement.executeQuery();
            if (!users_rs.next()) {
                System.out.println("           \u001B[31m" + "Bad login or password!" + "\u001B[0m");
                return null;
            }
            return new User(login, password);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        String DELETE_USER = "DELETE FROM users WHERE login = ? AND password = ?";
        try {
            var statement = connection.prepareStatement(DELETE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

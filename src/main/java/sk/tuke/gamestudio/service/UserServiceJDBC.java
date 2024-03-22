package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

import java.sql.*;

public class UserServiceJDBC extends Service implements UserService{
    @Override
    public User addUser(String login, String password) {
        String ADD_USER = String.format("INSERT INTO users VALUES (DEFAULT, '%s', '%s')", login, password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            connection.prepareStatement(ADD_USER).executeUpdate();
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
        String SELECT_USER = String.format("SELECT login FROM users WHERE login = '%s' AND password = '%s'", login, password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            ResultSet users_rs = connection.prepareStatement(SELECT_USER).executeQuery();
            if (!users_rs.next()) {
                System.out.println("           \u001B[31m" + "Bad login or password!" + "\u001B[0m");
                return null;
            }
            return new User(login, password);
        }
        catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        String DELETE_USER = String.format("DELETE FROM users WHERE login = '%s' AND password = '%s'", user.getLogin(), user.getPassword());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.prepareStatement(DELETE_USER).executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

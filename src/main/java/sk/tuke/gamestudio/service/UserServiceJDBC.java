package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

import java.sql.*;

public class UserServiceJDBC implements UserService{
    private final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    private final String USER = "postgres";
    private final String PASSWORD = "}bI1;8s=O,";
    @Override
    public User addUser(String login, String password) {
        String ADD_USER = String.format("INSERT INTO users VALUES (DEFAULT, '%s', '%s', DEFAULT)", login, password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            connection.prepareStatement(ADD_USER).executeUpdate();
            return new User(login, 0);
        } catch (SQLException e) {
            if (e.getErrorCode() == 0)
                System.out.println("           \u001B[31m" + "This login is already taken!" + "\u001B[0m");
            else
                System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User logIn(String login, String password) {
        String SELECT_USER = String.format("SELECT levels_completed FROM users WHERE login = '%s' AND password = '%s'", login, password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            ResultSet users_rs = connection.prepareStatement(SELECT_USER).executeQuery();
            if (!users_rs.next()) {
                System.out.println("           \u001B[31m" + "Bad login or password!" + "\u001B[0m");
                return null;
            }
            return new User(login, users_rs.getInt(1));
        }
        catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteUser(String login, String password) {
        String DELETE_USER = String.format("DELETE FROM users WHERE login = '%s' AND password = '%s'", login, password);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            connection.prepareStatement(DELETE_USER).executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }

    @Override
    public void addCompletedLevel(User user, int level) {
        if (level <= user.levelsCompleted())
            return;
        String ADD_LEVEL = String.format("UPDATE users SET levels_completed = %d WHERE login = '%s'", level, user.login());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            connection.prepareStatement(ADD_LEVEL).executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }
}

package sk.tuke.gamestudio.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import sk.tuke.gamestudio.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceJDBC implements UserService {
    private final Connection connection = DBService.getConnection();
    private final Argon2PasswordEncoder passwordEncoder = DBService.getPasswordEncoder();

    @Override
    public User addUser(String login, String password) {
        String ADD_USER = "INSERT INTO users (login, password) VALUES (?, ?)";
        String encryptedPassword = passwordEncoder.encode(password);
        try {
            var statement = connection.prepareStatement(ADD_USER);
            statement.setString(1, login);
            statement.setString(2, encryptedPassword);
            statement.executeUpdate();
            return new User(login, password);
        } catch (SQLException e) {
            if (e.getErrorCode() == 0)
                throw new GameStudioException(ExceptionConstants.LOGIN_ALREADY_TAKEN);
            else
                throw new GameStudioException(e);
        }
    }

    @Override
    public User logIn(String login, String password) {
        String SELECT_USER = "SELECT password FROM users WHERE login = ?";
        try {
            var statement = connection.prepareStatement(SELECT_USER);
            statement.setString(1, login);

            ResultSet rs = statement.executeQuery();
            if (!rs.next() || !passwordEncoder.matches(password, rs.getString("password"))) {
                throw new GameStudioException(ExceptionConstants.BAD_LOGIN_OR_PASSWORD);
            }
            return new User(login, password);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        String DELETE_USER = "DELETE FROM users WHERE login = ? AND password = ?";
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        try {
            var statement = connection.prepareStatement(DELETE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, encryptedPassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}

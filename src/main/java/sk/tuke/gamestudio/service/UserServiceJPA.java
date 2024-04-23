package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.User;

import javax.persistence.*;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceJPA implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Argon2PasswordEncoder passwordEncoder;

    @Override
    public User addUser(String login, String password) throws GameStudioException {
        if (login == null || password == null) {
            throw new GameStudioException("Login and password cannot be null");
        }
        if (login.length() < 3) {
            throw new GameStudioException("Login must have at least 3 characters");
        }
        if (password.length() < 6) {
            throw new GameStudioException("Password must have at least 6 characters");
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(login, hashedPassword);

        try {
            entityManager.persist(newUser);
            return newUser;
        } catch (Exception e) {
            throw new GameStudioException("This login is already taken!", e);
        }
    }

    @Override
    public User logIn(String login, String password) throws GameStudioException {
        if (login == null || password == null) {
            throw new GameStudioException("Login and password cannot be null");
        }

        try {
            String expectedPassword = entityManager.createNamedQuery("User.getEncodedPassword", String.class)
                    .setParameter("login", login)
                    .getSingleResult();

            if (!passwordEncoder.matches(password, expectedPassword)) {
                throw new GameStudioException("Bad login or password!");
            }

            return new User(login, expectedPassword);
        } catch (Exception e) {
            throw new GameStudioException("Bad login or password!", e);
        }
    }

    @Override
    public void deleteUser(User user) throws GameStudioException {
        if (user == null) {
            throw new GameStudioException("User cannot be null");
        }

        try {
            entityManager.createNamedQuery("User.deleteUser")
                    .setParameter("login", user.getLogin())
                    .setParameter("password", passwordEncoder.encode(user.getPassword()))
                    .executeUpdate();
        } catch (Exception e) {
            throw new GameStudioException("User does not exist!", e);
        }
    }
}

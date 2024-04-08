package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

import javax.persistence.*;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User addUser(String login, String password) throws GameStudioException {
        User newUser = new User(login, password);
        try {
            entityManager.persist(newUser);
            return newUser;
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public User logIn(String login, String password) throws GameStudioException {
        try {
            return entityManager.createNamedQuery("User.logIn", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void deleteUser(User user) throws GameStudioException {
        entityManager.createNamedQuery("User.deleteUser")
                .setParameter("login", user.getLogin())
                .setParameter("password", user.getPassword())
                .executeUpdate();
    }
}

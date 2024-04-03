package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User addUser(String login, String password) throws GameStudioException {
        entityManager.createNamedQuery("User.addUser")
                .setParameter("login", login)
                .setParameter("password", password)
                .executeUpdate();

        return new User(login, password);
    }

    @Override
    public User logIn(String login, String password) throws GameStudioException {
        return entityManager.createNamedQuery("User.logIn", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }

    @Override
    public void deleteUser(User user) throws GameStudioException {
        entityManager.createNamedQuery("User.deleteUser")
                .setParameter("login", user.getLogin())
                .setParameter("password", user.getPassword())
                .executeUpdate();
    }
}

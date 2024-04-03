package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCompletedLevel(Score score, int level) throws GameStudioException {
        if (level <= score.getLevelsCompleted())
            return;

        entityManager.createNamedQuery("Score.addCompletedLevel")
                .setParameter("levelsCompleted", level)
                .setParameter("completedAt", new Timestamp(new Date().getTime()))
                .setParameter("login", score.getLogin())
                .executeUpdate();
    }

    @Override
    public void addScore(User user) throws GameStudioException {
        if (user == null)   return;

        entityManager.createNamedQuery("Score.addScore")
                .setParameter("login", user.getLogin())
                .setParameter("completedAt", new Timestamp(new Date().getTime()))
                .executeUpdate();
    }

    @Override
    public Score getScore(User user) throws GameStudioException {
        if (user == null)   return null;

        return entityManager.createNamedQuery("Score.getScore", Score.class)
                .setParameter("login", user.getLogin())
                .getSingleResult();
    }

    @Override
    public List<Score> getTopScores() throws GameStudioException {
        return entityManager.createNamedQuery("Score.getTopScores", Score.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Score.reset").executeUpdate();
    }
}

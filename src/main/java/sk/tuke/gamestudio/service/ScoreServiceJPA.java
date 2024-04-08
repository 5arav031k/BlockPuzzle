package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

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
    public void addScore(String username) throws GameStudioException {
        try {
            entityManager.createNamedQuery("Score.addScore")
                    .setParameter("login", username)
                    .setParameter("completedAt", new Timestamp(new Date().getTime()))
                    .executeUpdate();
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public Score getScore(String username) throws GameStudioException {
        try {
            return entityManager.createNamedQuery("Score.getScore", Score.class)
                    .setParameter("login", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new GameStudioException(e);
        }
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

package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCompletedLevel(Score score, int level) throws GameStudioException {
        if (score == null || score.getLevelsCompleted() < 0 || score.getLevelsCompleted() > 6 || level < 0 || level > 6)
            throw new GameStudioException("Invalid score or level");

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
            entityManager.createNativeQuery("INSERT INTO Score (login, levels_completed, completed_at) VALUES (:login, 0, :completedAt)")
                    .setParameter("login", username)
                    .setParameter("completedAt", new Timestamp(new Date().getTime()))
                    .executeUpdate();
        } catch (Exception e) {
            throw new GameStudioException("Could not add score", e);
        }
    }

    @Override
    public Score getScore(String username) throws GameStudioException {
        try {
            return entityManager.createNamedQuery("Score.getScore", Score.class)
                    .setParameter("login", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new GameStudioException("Could not get score");
        }
    }

    @Override
    public List<Score> getTopScores() throws GameStudioException {
        try {
            return entityManager.createNamedQuery("Score.getTopScores", Score.class)
                    .setMaxResults(5)
                    .getResultList();
        } catch (Exception e) {
            throw new GameStudioException("Could not get top scores");
        }
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Score.reset").executeUpdate();
    }
}

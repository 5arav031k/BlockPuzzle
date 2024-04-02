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

        entityManager.createNamedQuery("Score.addCompletedLevel", Score.class)
                .setParameter("levelsCompleted", level)
                .setParameter("completedAt", new Timestamp(new Date().getTime()))
                .setParameter("login", score.getLogin())
                .executeUpdate();
    }

    @Override
    public Score addScore(User user) throws GameStudioException {
        return null;
    }

    @Override
    public Score getScore(User user) throws GameStudioException {
        return null;
    }

    @Override
    public List<Score> getTopScores() throws GameStudioException {
        return null;
    }

    @Override
    public void reset() throws GameStudioException {

    }
}

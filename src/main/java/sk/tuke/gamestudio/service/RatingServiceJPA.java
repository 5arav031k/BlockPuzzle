package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        if (rating == null || rating.getRating() < 1 || rating.getRating() > 5)
            throw new GameStudioException("Invalid rating");

        entityManager.createNamedQuery("Rating.setRating")
                .setParameter("login", rating.getLogin())
                .setParameter("rating", rating.getRating())
                .setParameter("ratedOn", new Timestamp(rating.getRatedOn().getTime()))
                .executeUpdate();
    }

    @Override
    public int getAverageRating() throws GameStudioException {
        Double rating = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                .getSingleResult();
        if (rating == null)
            return 0;

        return (int) Math.round(rating);
    }

    @Override
    public int getRating(String login) throws GameStudioException {
        int rating = entityManager.createNamedQuery("Rating.getRating", Integer.class)
                .setParameter("login", login)
                .getSingleResult();
        if (rating == 0)
            throw new GameStudioException("Rating does not exist for login: " + login);
        return rating;
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Rating.reset").executeUpdate();
    }
}

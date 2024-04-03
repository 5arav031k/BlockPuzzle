package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        entityManager.createNamedQuery("Rating.setRating")
                .setParameter("login", rating.getLogin())
                .setParameter("rating", rating.getRating())
                .setParameter("ratedOn", new Timestamp(rating.getRatedOn().getTime()))
                .executeUpdate();
    }

    @Override
    public int getAverageRating() throws GameStudioException {
        return entityManager.createNamedQuery("Rating.getAverageRating", Integer.class)
                .getSingleResult();
    }

    @Override
    public int getRating(User user) throws GameStudioException {
        return entityManager.createNamedQuery("Rating.getRating", Integer.class)
                .setParameter("login", user.getLogin())
                .getSingleResult();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNamedQuery("Rating.reset").executeUpdate();
    }
}

package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.User;

public interface RatingService {
    void setRating(Rating rating) throws GameStudioException;
    int getAverageRating() throws GameStudioException;
    int getRating(User user) throws GameStudioException;
    void reset() throws GameStudioException;
}

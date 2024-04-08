package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating) throws GameStudioException;
    int getAverageRating() throws GameStudioException;
    int getRating(String username) throws GameStudioException;
    void reset() throws GameStudioException;
}

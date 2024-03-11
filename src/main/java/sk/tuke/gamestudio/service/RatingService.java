package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

public interface RatingService {
    void addRating(User user, int rating);
    void reset();
}

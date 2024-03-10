package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface ScoreService {
    void addCompletedLevel(Score score, int level);
    Score addScore(User user);
    Score getScore(User user);
    List<Score> getTopScores(String game);
    void reset();
}

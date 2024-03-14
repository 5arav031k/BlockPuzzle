package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface ScoreService {
    void addCompletedLevel(Score score, int level) throws GameStudioException;
    Score addScore(User user) throws GameStudioException;
    Score getScore(User user) throws GameStudioException;
    List<Score> getTopScores() throws GameStudioException;
    void reset() throws GameStudioException;
}

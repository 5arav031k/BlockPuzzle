package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface ScoreService {
    void addCompletedLevel(Score score, int level) throws GameStudioException;
    Score addScore(User user) throws GameStudioException;
    Score getScore(User user) throws GameStudioException;
    List<Score> getTopScores() throws GameStudioException;
    void reset() throws GameStudioException;
}

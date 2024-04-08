package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addCompletedLevel(Score score, int level) throws GameStudioException;
    void addScore(String username) throws GameStudioException;
    Score getScore(String username) throws GameStudioException;
    List<Score> getTopScores() throws GameStudioException;
    void reset() throws GameStudioException;
}

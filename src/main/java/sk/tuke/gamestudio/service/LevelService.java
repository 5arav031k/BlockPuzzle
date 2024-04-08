package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Level;

public interface LevelService {
    Level getLevel(int levelID) throws GameStudioException;
}

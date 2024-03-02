package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;

public interface LevelService {
    Level getLevelJDBC(int levelID, Field field);
}

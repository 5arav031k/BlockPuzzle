package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.entity.Level;

import java.sql.Connection;
import java.sql.ResultSet;

public interface LevelService {
    Level getLevel(int levelID, Field field) throws GameStudioException;
    void generateShapes(Connection connection, ResultSet shapes_rs) throws GameStudioException;
}

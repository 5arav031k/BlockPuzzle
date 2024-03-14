package main.java.sk.tuke.gamestudio.entity;

import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<Shape> shapes;
    private final Field field;
    private int shapeCount;

    public Level(Field field) {
        this.field = field;
        shapes = new ArrayList<>();
    }

    public int getShapeCount() {
        return shapeCount;
    }
    public void setShapesCount(int shapeCount) {
        this.shapeCount = shapeCount;
    }
    public List<Shape> getShapes() {
        return shapes;
    }
    public Field getField() {
        return field;
    }
}

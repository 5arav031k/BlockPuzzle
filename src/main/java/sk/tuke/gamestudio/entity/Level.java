package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Level {
    private final List<Shape> shapes;
    private final Field field;
    @Setter
    private int shapeCount;

    public Level(Field field) {
        this.field = field;
        shapes = new ArrayList<>();
    }
}

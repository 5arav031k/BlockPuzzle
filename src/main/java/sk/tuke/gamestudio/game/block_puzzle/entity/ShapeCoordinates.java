package sk.tuke.gamestudio.game.block_puzzle.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ShapeCoordinates implements Serializable {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int firstMinX;
    private int firstMinY;

    public ShapeCoordinates() {
    }
}

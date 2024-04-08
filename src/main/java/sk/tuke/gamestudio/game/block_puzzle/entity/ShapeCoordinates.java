package sk.tuke.gamestudio.game.block_puzzle.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShapeCoordinates {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int firstMinX;
    private int firstMinY;

    public ShapeCoordinates() {
    }
}

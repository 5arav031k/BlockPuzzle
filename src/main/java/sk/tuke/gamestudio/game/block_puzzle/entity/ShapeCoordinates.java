package sk.tuke.gamestudio.game.block_puzzle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShapeCoordinates {
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int firstMinX;
    private int firstMinY;
}

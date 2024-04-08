package sk.tuke.gamestudio.game.block_puzzle.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ShapeTile implements Serializable {
    private int x;
    private int y;

    public ShapeTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ShapeTile() {
    }
}

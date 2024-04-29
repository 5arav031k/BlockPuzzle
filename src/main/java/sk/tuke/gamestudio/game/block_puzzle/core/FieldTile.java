package sk.tuke.gamestudio.game.block_puzzle.core;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldTile {
    private String value;
    private TileState tileState = TileState.NOT_MARKED;
}

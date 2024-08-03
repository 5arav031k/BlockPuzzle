package sk.tuke.gamestudio.game.block_puzzle.core;

import lombok.Getter;

@Getter
public class Field {
    private final FieldTile[][] map;
    private final int mapWidth = 50;
    private final int mapHeight = 11;
    private final int fieldWidth;
    private final int fieldHeight;

    public Field(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        map = new FieldTile[mapWidth][mapHeight];
        initialize();
    }

    private void initialize() {
        for (int row = 0; row < mapWidth; row++) {
            for (int col = 0; col < mapHeight; col++)
                map[row][col] = new FieldTile();
        }
        generateMapEdges();
        clearMap();
    }

    public void clearMap() {
        for (int row = 1; row < mapWidth - 1; row++) {
            for (int col = 1; col < mapHeight - 1; col++)
                map[row][col].setValue(" ");
        }
    }

    public void clearField() {
        for (int row = 3; row < fieldHeight + 3; row++) {
            for (int col = 8; col < fieldWidth * 2 + 8; col++)
                map[col][row].setValue(" ");
        }
    }

    public void generateMapEdges() {
        for (int row = 0; row < mapWidth; row++) {
            map[row][0].setValue("‾");
            map[row][mapHeight - 1].setValue("_");
        }

        for (int col = 0; col < mapHeight; col++) {
            map[0][col].setValue("|");
            map[mapWidth - 1][col].setValue("|");
        }
    }

    public void generateFieldEdges() {
        for (int col = 3; col < fieldHeight + 3; col++) {
            map[7][col].setValue("|");
            map[fieldWidth * 2 + 8][col].setValue("|");
        }

        for (int row = 8; row < fieldWidth * 2 + 8; row++) {
            map[row][2].setValue("_");
            map[row][fieldHeight + 3].setValue("‾");
        }
    }

    public boolean isSolved() {
        for (int col = 3; col < fieldHeight + 3; col++) {
            for (int row = 8; row < fieldWidth * 2 + 8; row++) {
                if (map[row][col].getValue().equals(" "))
                    return false;
            }
        }
        return true;
    }
}

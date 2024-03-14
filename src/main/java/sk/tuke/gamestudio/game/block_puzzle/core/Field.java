package main.java.sk.tuke.gamestudio.game.block_puzzle.core;

public class Field {
    private static FieldTile[][] map;
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


    public FieldTile[][] getMap() {
        return map;
    }
    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getFieldWidth() {
        return fieldWidth;
    }
    public int getFieldHeight() {
        return fieldHeight;
    }

    private void initialize() {
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++)
                map[i][j] = new FieldTile();
        }
        generateMapEdges();
        clearMap();
    }

    public void clearMap() {
        for (int i = 1; i < mapWidth -1; i++) {
            for (int j = 1; j < mapHeight -1; j++)
                map[i][j].setValue(" ");
        }
    }

    public void clearField() {
        for (int i = 3; i < fieldHeight+3; i++) {
            for (int j = 8; j < fieldWidth*2+8; j++)
                map[j][i].setValue(" ");
        }
    }

    public void generateMapEdges() {
        for (int i = 0; i < mapWidth; i++) {
            map[i][0].setValue("‾");
            map[i][mapHeight-1].setValue("_");
        }

        for (int i = 0; i < mapHeight; i++) {
            map[0][i].setValue("|");
            map[mapWidth-1][i].setValue("|");
        }
    }

    public void generateFieldEdges() {
        for (int i = 3; i < fieldHeight+3; i++) {
            map[7][i].setValue("|");
            map[fieldWidth*2+8][i].setValue("|");
        }

        for (int i = 8; i < fieldWidth*2+8; i++) {
            map[i][2].setValue("_");
            map[i][fieldHeight+3].setValue("‾");
        }
    }

    public boolean isSolved() {
        for (int i = 3; i < fieldHeight+3; i++) {
            for (int j = 8; j < fieldWidth*2+8; j++) {
                if (map[j][i].getValue().equals(" "))
                    return false;
            }
        }
        winMsg();
        return true;
    }

    private void winMsg() {
        System.out.println("                 \u001B[32m" + "CONGRATULATIONS!" + "\u001B[0m");
    }
}

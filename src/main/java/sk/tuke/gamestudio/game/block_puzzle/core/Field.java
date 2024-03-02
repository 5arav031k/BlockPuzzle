package main.java.sk.tuke.gamestudio.game.block_puzzle.core;

public class Field {
    private static String[][] map;
    private final int mapWidth = 50;
    private final int mapHeight = 10;
    private final int fieldWidth;
    private final int fieldHeight;

    public Field(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        map = new String[mapWidth][mapHeight];
        initialize();
    }


    public String[][] getMap() {
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
        generateMapEdges();
        clearMap();
    }
    public void clearMap() {
        for (int i = 1; i < mapWidth -1; i++) {
            for (int j = 1; j < mapHeight -1; j++)
                map[i][j] = " ";
        }
    }
    public void generateMapEdges() {
        for (int i = 0; i < mapWidth; i++) {
            map[i][0] = "‾";
            map[i][mapHeight-1] = "_";
        }

        for (int i = 0; i < mapHeight; i++) {
            map[0][i] = "|";
            map[mapWidth-1][i] = "|";
        }
    }
    public void generateTileEdges() {
        for (int i = 2; i < fieldHeight +2; i++) {
            map[7][i] = "|";
            map[fieldWidth *2+8][i] = "|";
        }

        for (int i = 8; i < fieldWidth *2+8; i++) {
            map[i][1] = "_";
            map[i][fieldHeight +2] = "‾";
        }
    }
    public boolean isSolved() {
        for (int i = 2; i < fieldHeight+2; i++) {
            for (int j = 8; j < fieldWidth*2+8; j++) {
                if (map[j][i].equals(" "))
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

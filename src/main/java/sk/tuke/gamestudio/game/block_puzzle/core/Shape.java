package main.java.sk.tuke.gamestudio.game.block_puzzle.core;

import main.java.sk.tuke.gamestudio.game.block_puzzle.entity.ShapeCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    private final List<Tile> shape;
    private final String shapeColor;
    private final ShapeCoordinates coordinates;
    private Field field;
    private boolean isPlacedOnField;
    private int shapeWidth;
    private int shapeHeight;
    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
        shape = new ArrayList<>();
        coordinates = new ShapeCoordinates();
        isPlacedOnField = false;
    }

    public int getShapeWidth() {
        return shapeWidth;
    }
    public void setShapeWidth(int shapeWidth) {
        this.shapeWidth = shapeWidth;
    }
    public int getShapeHeight() {
        return shapeHeight;
    }
    public void setShapeHeight(int shapeHeight) {
        this.shapeHeight = shapeHeight;
    }
    public void setField(Field field) {
        this.field = field;
    }
    public ShapeCoordinates getCoordinates() {
        return coordinates;
    }
    public boolean isPlacedOnField() {
        return isPlacedOnField;
    }
    public List<Tile> getShape() {
        return shape;
    }
    public String getShapeColor() {
        return shapeColor;
    }

    public void moveUp() {
        if (coordinates.getMinY() == 2)
            return;
        shape.forEach(tile -> tile.setY(tile.getY()-1));
        coordinates.setMinY(coordinates.getMinY()-1);
        coordinates.setMaxY(coordinates.getMaxY()-1);
    }

    public void moveDown() {
        if (coordinates.getMaxY() == 1+field.getFieldHeight())
            return;
        shape.forEach(tile -> tile.setY(tile.getY()+1));
        coordinates.setMinY(coordinates.getMinY()+1);
        coordinates.setMaxY(coordinates.getMaxY()+1);
    }

    public void moveLeft() {
        if (coordinates.getMinX() == 8)
            return;
        shape.forEach(tile -> tile.setX(tile.getX()-2));
        coordinates.setMinX(coordinates.getMinX()-2);
        coordinates.setMaxX(coordinates.getMaxX()-2);
    }

    public void moveRight() {
        if (coordinates.getMaxX() == 7+field.getFieldWidth()*2)
            return;
        shape.forEach(tile -> tile.setX(tile.getX()+2));
        coordinates.setMinX(coordinates.getMinX()+2);
        coordinates.setMaxX(coordinates.getMaxX()+2);
    }

    public void placeShapeToField() {
        if (!isPlacedOnField) {
            coordinates.setMinX(coordinates.getFirstMinX());
            coordinates.setMinY(coordinates.getFirstMinY());
            coordinates.setMaxX(shapeWidth-1);
            coordinates.setMaxY(shapeHeight-1);
        }
        else
            return;

        for (Tile tile : shape) {
            int y = tile.getY()-coordinates.getMinY()+2;
            tile.setY(y);

            int x = tile.getX()-coordinates.getMinX()+8;
            tile.setX(x);
        }
        coordinates.setMinX(8);
        coordinates.setMinY(2);
        coordinates.setMaxX(coordinates.getMaxX()+coordinates.getMinX());
        coordinates.setMaxY(coordinates.getMaxY()+coordinates.getMinY());
        isPlacedOnField = true;
    }

    public void hideShape() {
        isPlacedOnField = false;
        int x = coordinates.getFirstMinX() - coordinates.getMinX();
        int y = coordinates.getFirstMinY() - coordinates.getMinY();

        for (Tile tile : shape) {
            tile.setX(tile.getX()+x);
            tile.setY(tile.getY()+y);
        }
    }

    public void markShapeIdx(Field field, String shapeIdx) {
        String[][] map = field.getMap();
        for (int i = 0; i < field.getMapHeight(); i++) {
            for (int j = 0; j < field.getMapWidth(); j++) {
                if (map[j][i].equals(shapeIdx)) {
                    map[j-1][i] = "\u001B[32m" + "(" + "\u001B[0m";
                    map[j][i] = "\u001B[32m" + shapeIdx + "\u001B[0m";
                    map[j+1][i] = "\u001B[32m" + ")" + "\u001B[0m";
                }
            }
        }
    }

    public void unmarkShapeIdx(Field field, String shapeIdx) {
        String[][] map = field.getMap();
        for (int i = 0; i < field.getMapHeight(); i++) {
            for (int j = 0; j < field.getMapWidth(); j++) {
                if (map[j][i].equals("\u001B[32m" + shapeIdx + "\u001B[0m")) {
                    map[j-1][i] = "(";
                    map[j][i] = shapeIdx;
                    map[j+1][i] = ")";
                }
            }
        }
    }
}

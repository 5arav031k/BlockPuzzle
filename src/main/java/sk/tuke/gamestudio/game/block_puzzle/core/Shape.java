package sk.tuke.gamestudio.game.block_puzzle.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.game.block_puzzle.entity.ShapeCoordinates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shape implements Serializable {
    @Getter
    private List<ShapeTile> shape;
    @Getter
    private String shapeColor;
    @Getter
    private ColorEnum shapeColorEnum;
    @Getter
    private ShapeCoordinates coordinates;
    @Setter
    @Getter
    private int shapeNumber;
    @Setter
    private boolean isPlacedOnField;
    @Setter
    private int shapeWidth;
    @Setter
    private int shapeHeight;
    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
        shapeColorEnum = Color.getColorEnum(shapeColor);
        shape = new ArrayList<>();
        coordinates = new ShapeCoordinates();
        isPlacedOnField = false;
    }

    public Shape(String shapeColor, int shapeNumber, int shapeWidth, int shapeHeight) {
        this.shapeColor = shapeColor;
        this.shapeNumber = shapeNumber;
        this.shapeWidth = shapeWidth;
        this.shapeHeight = shapeHeight;
        shapeColorEnum = Color.getColorEnum(shapeColor);
        shape = new ArrayList<>();
        coordinates = new ShapeCoordinates();
        isPlacedOnField = false;
    }

    public Shape() {
    }

    public boolean isPlacedOnField() {
        return isPlacedOnField;
    }

    public void moveUp() {
        if (coordinates.getMinY() == 3)
            return;
        shape.forEach(shapeTile -> shapeTile.setY(shapeTile.getY()-1));
        coordinates.setMinY(coordinates.getMinY()-1);
        coordinates.setMaxY(coordinates.getMaxY()-1);
    }

    public void moveDown(Field field) {
        if (coordinates.getMaxY() == 2+field.getFieldHeight())
            return;
        shape.forEach(shapeTile -> shapeTile.setY(shapeTile.getY()+1));
        coordinates.setMinY(coordinates.getMinY()+1);
        coordinates.setMaxY(coordinates.getMaxY()+1);
    }

    public void moveLeft() {
        if (coordinates.getMinX() == 8)
            return;
        shape.forEach(shapeTile -> shapeTile.setX(shapeTile.getX()-2));
        coordinates.setMinX(coordinates.getMinX()-2);
        coordinates.setMaxX(coordinates.getMaxX()-2);
    }

    public void moveRight(Field field) {
        if (coordinates.getMaxX() == 7+field.getFieldWidth()*2)
            return;
        shape.forEach(shapeTile -> shapeTile.setX(shapeTile.getX()+2));
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

        for (ShapeTile shapeTile : shape) {
            int x = shapeTile.getX()-coordinates.getMinX()+8;
            shapeTile.setX(x);

            int y = shapeTile.getY()-coordinates.getMinY()+3;
            shapeTile.setY(y);
        }
        coordinates.setMinX(8);
        coordinates.setMinY(3);
        coordinates.setMaxX(coordinates.getMaxX()+coordinates.getMinX());
        coordinates.setMaxY(coordinates.getMaxY()+coordinates.getMinY());
        isPlacedOnField = true;
    }

    public void hideShape() {
        isPlacedOnField = false;
        int x = coordinates.getFirstMinX() - coordinates.getMinX();
        int y = coordinates.getFirstMinY() - coordinates.getMinY();

        for (ShapeTile shapeTile : shape) {
            shapeTile.setX(shapeTile.getX()+x);
            shapeTile.setY(shapeTile.getY()+y);
        }
    }

    public void markShapeIdx(Field field, String shapeIdx) {
        FieldTile[][] map = field.getMap();
        for (int col = 0; col < field.getMapHeight(); col++) {
            for (int row = 0; row < field.getMapWidth(); row++) {
                if (map[row][col].getValue().equals(shapeIdx)) {
                    map[row-1][col].setValue("\u001B[32m" + "(" + "\u001B[0m");
                    map[row][col].setValue("\u001B[32m" + shapeIdx + "\u001B[0m");
                    map[row+1][col].setValue("\u001B[32m" + ")" + "\u001B[0m");
                }
            }
        }
    }

    public void unmarkShapeIdx(Field field, String shapeIdx) {
        FieldTile[][] map = field.getMap();
        for (int col = 0; col < field.getMapHeight(); col++) {
            for (int row = 0; row < field.getMapWidth(); row++) {
                if (map[row][col].getValue().equals("\u001B[32m" + shapeIdx + "\u001B[0m")) {
                    map[row-1][col].setValue("(");
                    map[row][col].setValue(shapeIdx);
                    map[row+1][col].setValue(")");
                }
            }
        }
    }
}

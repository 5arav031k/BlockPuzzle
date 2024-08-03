package sk.tuke.gamestudio.game.block_puzzle.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.game.block_puzzle.entity.ShapeCoordinates;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Shape {
    private final List<ShapeTile> shape;
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

    public void moveUp() {
        if (coordinates.getMinY() == 3)
            return;
        shape.forEach(shapeTile -> shapeTile.setY(shapeTile.getY() - 1));
        coordinates.setMinY(coordinates.getMinY() - 1);
        coordinates.setMaxY(coordinates.getMaxY() - 1);
    }

    public void moveDown() {
        if (coordinates.getMaxY() == 2 + field.getFieldHeight())
            return;
        shape.forEach(shapeTile -> shapeTile.setY(shapeTile.getY() + 1));
        coordinates.setMinY(coordinates.getMinY() + 1);
        coordinates.setMaxY(coordinates.getMaxY() + 1);
    }

    public void moveLeft() {
        if (coordinates.getMinX() == 8)
            return;
        shape.forEach(shapeTile -> shapeTile.setX(shapeTile.getX() - 2));
        coordinates.setMinX(coordinates.getMinX() - 2);
        coordinates.setMaxX(coordinates.getMaxX() - 2);
    }

    public void moveRight() {
        if (coordinates.getMaxX() == 7 + field.getFieldWidth() * 2)
            return;
        shape.forEach(shapeTile -> shapeTile.setX(shapeTile.getX() + 2));
        coordinates.setMinX(coordinates.getMinX() + 2);
        coordinates.setMaxX(coordinates.getMaxX() + 2);
    }

    public void placeShapeToField() {
        if (!isPlacedOnField) {
            coordinates.setMinX(coordinates.getFirstMinX());
            coordinates.setMinY(coordinates.getFirstMinY());
            coordinates.setMaxX(shapeWidth - 1);
            coordinates.setMaxY(shapeHeight - 1);
        } else
            return;

        for (ShapeTile shapeTile : shape) {
            int x = shapeTile.getX() - coordinates.getMinX() + 8;
            shapeTile.setX(x);

            int y = shapeTile.getY() - coordinates.getMinY() + 3;
            shapeTile.setY(y);
        }
        coordinates.setMinX(8);
        coordinates.setMinY(3);
        coordinates.setMaxX(coordinates.getMaxX() + coordinates.getMinX());
        coordinates.setMaxY(coordinates.getMaxY() + coordinates.getMinY());
        isPlacedOnField = true;
    }

    public void hideShape() {
        isPlacedOnField = false;
        int x = coordinates.getFirstMinX() - coordinates.getMinX();
        int y = coordinates.getFirstMinY() - coordinates.getMinY();

        for (ShapeTile shapeTile : shape) {
            shapeTile.setX(shapeTile.getX() + x);
            shapeTile.setY(shapeTile.getY() + y);
        }
    }

    public void markShapeIdx(Field field, String shapeIdx) {
        for (int col = 0; col < field.getMapHeight(); col++) {
            for (int row = 0; row < field.getMapWidth(); row++) {
                if (field.getMap()[row][col].getValue().equals(shapeIdx)) {
                    field.getMap()[row - 1][col].setValue("\u001B[32m" + "(" + "\u001B[0m");
                    field.getMap()[row][col].setValue("\u001B[32m" + shapeIdx + "\u001B[0m");
                    field.getMap()[row + 1][col].setValue("\u001B[32m" + ")" + "\u001B[0m");
                }
            }
        }
    }

    public void unmarkShapeIdx(Field field, String shapeIdx) {
        for (int col = 0; col < field.getMapHeight(); col++) {
            for (int row = 0; row < field.getMapWidth(); row++) {
                if (field.getMap()[row][col].getValue().equals("\u001B[32m" + shapeIdx + "\u001B[0m")) {
                    field.getMap()[row - 1][col].setValue("(");
                    field.getMap()[row][col].setValue(shapeIdx);
                    field.getMap()[row + 1][col].setValue(")");
                }
            }
        }
    }
}

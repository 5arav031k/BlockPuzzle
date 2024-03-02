package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.*;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.service.UserService;
import main.java.sk.tuke.gamestudio.service.UserServiceJDBC;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Level level;
    private final Field field;
    boolean isFirstCommand;
    private final Scanner console;
    private Shape currentShape;
    private int currentShapeIdx = 0;
    private boolean shapeIsMarked;
    private final LevelMenuConsoleUI levelMenu;
    private final StartMenuConsoleUI startMenu;
    private final UserService userService;
    private User user;

    public ConsoleUI() {
        field = new Field(5, 4);
        isFirstCommand = true;
        shapeIsMarked = false;
        console = new Scanner(System.in);
        startMenu = new StartMenuConsoleUI(field);
        levelMenu = new LevelMenuConsoleUI(field);
        userService = new UserServiceJDBC();
    }

    public void play() {
        logIn();
        field.clearMap();
        selectLevel();
        field.generateTileEdges();
        while (true) {
            addShapesToMap();
            drawMap();
            if (field.isSolved())
                break;
            processInput();
            clearField();
        }
        userService.addCompletedLevel(user, levelMenu.getSelectedLevel());
    }
    private void logIn() {
        startMenu.generateLogInPrompt();
        drawMap();

        while (!startMenu.isUserLogIn())
            startMenu.parseInput();

        user = startMenu.getUser();
    }
    private void selectLevel() {
        levelMenu.setUser(user);
        levelMenu.generateLevelMenu();
        drawMap();
        field.clearMap();

        while (!levelMenu.isLevelSelected()) {
            levelMenu.parseInput();
        }
        level = levelMenu.getLevel();
    }
    private void drawMap() {
        for (int i = 0; i < field.getMapHeight(); i++) {
            for (int j = 0; j < field.getMapWidth(); j++) {
                System.out.print(field.getMap()[j][i]);
            }
            System.out.println();
        }
    }
    private void addShapesToMap() {
        List<Shape> shapes = level.getShapes();
        for (Shape shape : shapes) {
            for (Tile tile : shape.getShape()) {
                int tileX = tile.getX();
                int tileY = tile.getY();
                if (!field.getMap()[tileX][tileY].equals(" ") && tileX >= 8 && tileX <= (7+field.getFieldWidth()*2)
                        && tileY >= 2 && tileY <= (1+field.getFieldHeight()))
                {
                    field.getMap()[tileX][tileY] = Color.MARKED;
                    shapeIsMarked = true;
                }
                else
                    field.getMap()[tile.getX()][tile.getY()] = shape.getShapeColor();
            }
        }
    }
    private void clearField() {
        shapeIsMarked = false;
        for (int i = 2; i < field.getFieldHeight()+2; i++) {
            for (int j = 8; j < field.getFieldWidth()*2+8; j++) {
                field.getMap()[j][i] = " ";
            }
        }
    }
    private void processInput() {
        if (!isFirstCommand && !currentShape.isPlacedOnField())
            isFirstCommand = true;

        System.out.print(promptMsg());
        String command = console.nextLine().trim().toUpperCase();

        if (command.equals("E"))
            System.exit(0);

        if (!isFirstCommand && command.matches("([1-" + level.getShapeCount() + "])") && currentShapeIdx == Integer.parseInt(command)) {
            currentShape.unmarkShapeIdx(field, String.valueOf(currentShapeIdx));
            currentShape.hideShape();
            currentShapeIdx = 0;
            return;
        }

        if (!shapeIsMarked && command.matches("([1-" + level.getShapeCount() + "])"))
            selectShape(command);
        else if (!isFirstCommand && command.matches("([UDLR])"))
            moveShape(command);
        else
            printErrors(command);
    }
    private String promptMsg() {
        String message;
        if (isFirstCommand)
            message = "Choose a shape (1-" + level.getShapeCount() + "): ";
        else if (shapeIsMarked)
            message = "Enter a command (U,D,L,R)\n" +
                    "or (" + (currentShapeIdx) + ") to hide a shape: ";
        else
            message = "Enter a command (U,D,L,R)\nor choose a shape (1-" + level.getShapeCount() + "): ";

        return message;
    }
    private void selectShape(String command) {
        if (!isFirstCommand)
            currentShape.unmarkShapeIdx(field, String.valueOf(currentShapeIdx));
        else
            isFirstCommand = false;

        currentShape = level.getShapes().get(Integer.parseInt(command)-1);
        currentShapeIdx = level.getShapes().indexOf(currentShape)+1;
        currentShape.placeShapeToField();
        currentShape.markShapeIdx(field, command);
    }
    private void moveShape(String command) {
        switch (command) {
            case "U": currentShape.moveUp(); break;
            case "D": currentShape.moveDown(); break;
            case "L": currentShape.moveLeft(); break;
            case "R": currentShape.moveRight(); break;
        }
    }
    private void printErrors(String command) {
        if (!command.matches("([1-" + level.getShapeCount() + "])") && !command.matches("([UDLR])")) {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
            return;
        }
        if (isFirstCommand)
            System.out.println("          \u001B[31m" + "Choose a shape first!" + "\u001B[0m");
        else if (shapeIsMarked)
            System.out.println("          \u001B[31m" + "Move the shape or hide it!" + "\u001B[0m");
        else
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
    }
}

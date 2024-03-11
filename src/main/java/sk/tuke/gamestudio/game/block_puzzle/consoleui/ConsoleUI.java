package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.*;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.service.ScoreService;
import main.java.sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Level level;
    private final Field field;
    private Score score;
    private Shape currentShape;
    private final LevelMenuConsoleUI levelMenu;
    private final StartMenuConsoleUI startMenu;
    private final ScoreService scoreService;
    private final Scanner console;
    private int currentShapeIdx = 0;
    private boolean shapeIsMarked;
    boolean isFirstCommand;

    public ConsoleUI() {
        field = new Field(5, 4);
        isFirstCommand = true;
        shapeIsMarked = false;
        console = new Scanner(System.in);
        startMenu = new StartMenuConsoleUI(field);
        levelMenu = new LevelMenuConsoleUI(field);
        scoreService = new ScoreServiceJDBC();
    }

    public void play() {
        logIn();
        field.clearMap();
        selectLevel();
        field.generateFieldEdges();
        while (true) {
            addShapesToMap();
            drawMap();
            if (field.isSolved())
                break;
            processInput();
            shapeIsMarked = false;
            field.clearField();
        }
        scoreService.addCompletedLevel(score, levelMenu.getSelectedLevel());
        showTopScores();
    }
    private void logIn() {
        startMenu.generateLogInPrompt();
        drawMap();

        while (!startMenu.isUserLogIn())
            startMenu.parseInput();

        score = startMenu.getScore();
    }
    private void selectLevel() {
        levelMenu.setScore(score);
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
                System.out.print(field.getMap()[j][i].getValue());
            }
            System.out.println();
        }
    }
    private void showTopScores() {
        System.out.println("\u001B[33m" + "Top 5 scores:" + "\u001B[0m");
        scoreService.getTopScores()
                .forEach(score -> System.out.println("\u001B[33m"+score.login()+
                        "    \u001B[31m"+"max level: \u001B[33m"+score.levelsCompleted()+
                        "\u001B[0m    \u001B[31m"+" completed at: \u001B[33m"+score.date()+"\u001B[0m"));
    }
    private void addShapesToMap() {
        List<Shape> shapes = level.getShapes();
        for (Shape shape : shapes) {
            for (ShapeTile shapeTile : shape.getShape()) {
                int tileX = shapeTile.getX();
                int tileY = shapeTile.getY();
                if (!field.getMap()[tileX][tileY].getValue().equals(" ")
                        && tileX >= 8 && tileX <= (7+field.getFieldWidth()*2)
                        && tileY >= 3 && tileY <= (2+field.getFieldHeight()))
                {
                    field.getMap()[tileX][tileY].setValue(Color.MARKED);
                    shapeIsMarked = true;
                }
                else
                    field.getMap()[shapeTile.getX()][shapeTile.getY()].setValue(shape.getShapeColor());
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

package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.*;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.service.*;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Level level;
    private final Field field;
    private Score score;
    private User user;
    private Shape currentShape;
    private final LevelMenuConsoleUI levelMenu;
    private final StartMenuConsoleUI startMenu;
    private final ScoreService scoreService;
    private final RatingService ratingService;
    private final CommentService commentService;
    private final Scanner console;
    private int currentShapeIdx = 0;
    private boolean shapeIsMarked;
    private boolean isFirstCommand;

    public ConsoleUI() {
        field = new Field(5, 4);
        isFirstCommand = true;
        shapeIsMarked = false;
        console = new Scanner(System.in);
        startMenu = new StartMenuConsoleUI(field);
        levelMenu = new LevelMenuConsoleUI(field);
        scoreService = new ScoreServiceJDBC();
        ratingService = new RatingServiceJDBC();
        commentService = new CommentServiceJDBC();
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
        rateBlockPuzzle();
    }

    private void logIn() {
        startMenu.generateLogInPrompt();
        drawMap();

        while (!startMenu.isUserLogIn())
            startMenu.parseInput();

        score = startMenu.getScore();
        user = startMenu.getUser();
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
        System.out.println("\n"+"\u001B[33m" + "Top 5 scores:" + "\u001B[0m");
        scoreService.getTopScores()
                .forEach(score -> System.out.println("\u001B[33m"+score.login()+
                        "    \u001B[31m"+"max level: \u001B[33m"+score.levelsCompleted()+
                        "\u001B[0m    \u001B[31m"+" completed at: \u001B[33m"+score.date()+"\u001B[0m"));
    }

    private void rateBlockPuzzle() {
        System.out.print("\n"+"\u001B[34m"+"Would you like to rate our game? (Y/N): "+"\u001B[0m");
        String input;

        while (!(input = console.nextLine()).equalsIgnoreCase("Y")) {
            if (input.equalsIgnoreCase("N"))    return;
            System.out.print("          \u001B[31m" + "Bad input! Please enter Y or N: " + "\u001B[0m");
        }

        userRatingBlockPuzzle();
        userCommentBlockPuzzle();

        System.out.println("\n"+"\u001B[32m"+"Thank you for your rating!"+"\u001B[0m");
    }

    private void userRatingBlockPuzzle() {
        System.out.print("\n"+"\u001B[34m"+"Please rate our game from 1 to 5: "+"\u001B[0m");
        String input;

        while (!(input = console.nextLine()).matches("([1-5])")) {
            System.out.print("          \u001B[31m"+"Invalid rating! Please enter a number from 1 to 5: "+"\u001B[0m");
        }
        int rating = Integer.parseInt(input);

        ratingService.addRating(user, rating);
    }

    private void userCommentBlockPuzzle() {
        System.out.println("\n"+"\u001B[34m"+"Please leave your comments (max 300 characters):"+"\u001B[0m");
        String input;

        while ((input = console.nextLine()).length() > 300) {
            System.out.println("          \u001B[31m"+"Comments are too long! Please keep them within 300 characters:"+"\u001B[0m");
        }
        String comment = input.trim();
        if (!comment.isEmpty())
            commentService.addComment(user, comment);
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

        if (command.equals("EXIT"))
            System.exit(0);

        if (!isFirstCommand && command.matches("([1-" + level.getShapeCount() + "])") && currentShapeIdx == Integer.parseInt(command)) {
            currentShape.unmarkShapeIdx(field, String.valueOf(currentShapeIdx));
            currentShape.hideShape();
            currentShapeIdx = 0;
            return;
        }

        if (!shapeIsMarked && command.matches("([1-" + level.getShapeCount() + "])"))
            selectShape(command);
        else if (!isFirstCommand && command.matches("([UDLRX])"))
            moveShape(command);
        else
            printErrors(command);
    }

    private String promptMsg() {
        String message;
        if (isFirstCommand)
            message = "Choose a shape (1-" + level.getShapeCount() + "): ";
        else if (shapeIsMarked)
            message = "Enter a command (U,D,L,R), (X) for reset\n" +
                    "or (" + (currentShapeIdx) + ") to hide a shape: ";
        else
            message = "Enter a command (U,D,L,R), (X) for reset\n" +
                    "or choose a shape (1-" + level.getShapeCount() + "): ";

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

    private void resetShapes() {
        if (currentShapeIdx != 0) {
            currentShape.unmarkShapeIdx(field, String.valueOf(currentShapeIdx));
            currentShapeIdx = 0;
        }

        level.getShapes().forEach(shape -> {
            if (shape.isPlacedOnField())
                shape.hideShape();
        });
    }

    private void moveShape(String command) {
        switch (command) {
            case "U": currentShape.moveUp(); break;
            case "D": currentShape.moveDown(); break;
            case "L": currentShape.moveLeft(); break;
            case "R": currentShape.moveRight(); break;
            case "X": resetShapes(); break;
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
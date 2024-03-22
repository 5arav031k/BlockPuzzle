package sk.tuke.gamestudio.game.block_puzzle.consoleui;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.block_puzzle.core.Color;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.service.LevelService;
import sk.tuke.gamestudio.service.LevelServiceJDBC;

import java.util.Scanner;

public class LevelMenuConsoleUI {
    private final Field field;
    private Score score;
    private Level level;
    private final Scanner console;
    private int selectedLevel;
    private boolean isLevelSelected;
    public LevelMenuConsoleUI(Field field) {
        this.field = field;
        isLevelSelected = false;
        console = new Scanner(System.in);
    }

    public void setScore(Score score) {
        this.score = score;
    }
    public int getSelectedLevel() {
        return selectedLevel;
    }
    public boolean isLevelSelected() {
        return isLevelSelected;
    }
    public Level getLevel() {
        return level;
    }

    public void generateLevelMenu() {
        for (int col = 1; col < 4; col++) {
            for (int row : new int[]{3, 14, 19, 30, 35, 46}) {
                field.getMap()[row][col].setValue("|");
                field.getMap()[row][col+6].setValue("|");
            }
        }

        for (int row = 4; row < 14; row++) {
            for (int col : new int[]{1, 3, 7, 9}) {
                field.getMap()[row][col].setValue(col == 3 || col == 9 ? "_" : "‾");
                field.getMap()[row+16][col].setValue(col == 3 || col == 9 ? "_" : "‾");
                field.getMap()[row+32][col].setValue(col == 3 || col == 9 ? "_" : "‾");
            }
        }

        for (int row = 6, levelNumber = 1; row < 39; row += 16, levelNumber++) {
            String[] values = {"L", "e", "v", "e", "l", String.valueOf(levelNumber)};
            for (int s = 0; s < values.length; s++)
                field.getMap()[row+s][2].setValue(values[s]);
        }

        for (int row = 6, levelNumber = 4; row < 39; row+= 16, levelNumber++) {
            String[] values = {"L", "e", "v", "e", "l", String.valueOf(levelNumber)};
            for (int s = 0; s < values.length; s++)
                field.getMap()[row+s][8].setValue(values[s]);
        }
        markLevels();
        generateGameName();
    }

    private void generateGameName() {
        String[] name = "█▀▀▀█▄█▀██▀▀▀█▄ Block❀Puzzle ▄█▀▀▀█▄█▀██▀▀▀█".split("");
        for (int row = 0; row < name.length; row++) {
            field.getMap()[row+3][5].setValue(name[row]);
        }
        field.getMap()[3][5].setValue("\u001B[32m█");
        field.getMap()[17][5].setValue("▄\u001B[0m");

        field.getMap()[19][5].setValue("\u001B[31mB");
        field.getMap()[30][5].setValue("e\u001B[0m");

        field.getMap()[32][5].setValue("\u001B[32m▄");
        field.getMap()[46][5].setValue("█\u001B[0m");
    }

    private void markLevels() {
        //levels 1 to 3
        for (int row = 3, row1 = 14, levelNumber = 0; row <= 36; row += 16, row1 += 16, levelNumber++) {
            String color = score.getLevelsCompleted() > levelNumber ? Color.GREEN_BACKGROUND :
                    score.getLevelsCompleted() == levelNumber ? Color.YELLOW_BACKGROUND : Color.BLACK_BACKGROUND;

            for (int col = 1; col <= 3; col++) {
                field.getMap()[row][col].setValue(color + field.getMap()[row][col].getValue());
                field.getMap()[row1][col].setValue(field.getMap()[row1][col].getValue() + Color.RESET);
            }
        }

        //levels 4 to 6
        for (int row = 3, row1 = 14, levelNumber = 3; row <= 36; row += 16, row1 += 16, levelNumber++) {
            String color = score.getLevelsCompleted() > levelNumber ? Color.GREEN_BACKGROUND :
                    score.getLevelsCompleted() == levelNumber ? Color.YELLOW_BACKGROUND : Color.BLACK_BACKGROUND;
            for (int col = 7; col <= 9; col++) {
                field.getMap()[row][col].setValue(color + field.getMap()[row][col].getValue());
                field.getMap()[row1][col].setValue(field.getMap()[row1][col].getValue() + Color.RESET);
            }
        }
    }

    public void parseInput() {
        System.out.print("Choose level: ");
        String command = console.nextLine().trim().toUpperCase();
        if (command.equals("EXIT"))
            System.exit(0);

        try {
            selectedLevel = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
            return;
        }

        if (command.matches("([1-" + (score.getLevelsCompleted()+1) + "])")) {
            LevelService levelService = new LevelServiceJDBC();
            level = levelService.getLevel(selectedLevel, field);
            isLevelSelected = true;
        }
        else {
            if (selectedLevel > score.getLevelsCompleted()+1 && selectedLevel <= 6)
                System.out.println("          \u001B[31m" + "You should complete level " + (score.getLevelsCompleted()+1) + " first!" + "\u001B[0m");
            else
                System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
        }
    }
}
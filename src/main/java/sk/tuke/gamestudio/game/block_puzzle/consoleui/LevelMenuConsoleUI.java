package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Color;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.entity.Level;
import main.java.sk.tuke.gamestudio.service.LevelService;
import main.java.sk.tuke.gamestudio.service.LevelServiceJDBC;

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
        for (int i = 1; i < 4; i++) {
            for (int j : new int[]{3, 14, 19, 30, 35, 46}) {
                field.getMap()[j][i].setValue("|");
                field.getMap()[j][i+6].setValue("|");
            }
        }

        for (int i = 4; i < 14; i++) {
            for (int j : new int[]{1, 3, 7, 9}) {
                field.getMap()[i][j].setValue(j == 3 || j == 9 ? "_" : "‾");
                field.getMap()[i+16][j].setValue(j == 3 || j == 9 ? "_" : "‾");
                field.getMap()[i+32][j].setValue(j == 3 || j == 9 ? "_" : "‾");
            }
        }

        for (int i = 6, j = 1; i < 39; i += 16, j++) {
            String[] values = {"L", "e", "v", "e", "l", String.valueOf(j)};
            for (int s = 0; s < values.length; s++)
                field.getMap()[i+s][2].setValue(values[s]);
        }

        for (int i = 6, j = 4; i < 39; i+= 16, j++) {
            String[] values = {"L", "e", "v", "e", "l", String.valueOf(j)};
            for (int s = 0; s < values.length; s++)
                field.getMap()[i+s][8].setValue(values[s]);
        }
        markLevels();
        generateGameName();
    }

    private void generateGameName() {
        String[] name = "█▀▀▀█▄█▀██▀▀▀█▄ Block❀Puzzle ▄█▀▀▀█▄█▀██▀▀▀█".split("");
        for (int i = 0; i < name.length; i++) {
            field.getMap()[i+3][5].setValue(name[i]);
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
        for (int a = 3, b = 14, j = 0; a <= 36; a += 16, b += 16, j++) {
            String color = score.levelsCompleted() > j ? Color.GREEN_BACKGROUND :
                    score.levelsCompleted() == j ? Color.YELLOW_BACKGROUND : Color.BLACK_BACKGROUND;

            for (int i = 1; i <= 3; i++) {
                field.getMap()[a][i].setValue(color + field.getMap()[a][i].getValue());
                field.getMap()[b][i].setValue(field.getMap()[b][i].getValue() + Color.RESET);
            }
        }

        //levels 4 to 6
        for (int a = 3, b = 14, j = 3; a <= 36; a += 16, b += 16, j++) {
            String color = score.levelsCompleted() > j ? Color.GREEN_BACKGROUND :
                    score.levelsCompleted() == j ? Color.YELLOW_BACKGROUND : Color.BLACK_BACKGROUND;
            for (int i = 7; i <= 9; i++) {
                field.getMap()[a][i].setValue(color + field.getMap()[a][i].getValue());
                field.getMap()[b][i].setValue(field.getMap()[b][i].getValue() + Color.RESET);
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

        if (command.matches("([1-" + (score.levelsCompleted()+1) + "])")) {
            LevelService levelService = new LevelServiceJDBC();
            level = levelService.getLevel(selectedLevel, field);
            isLevelSelected = true;
        }
        else {
            if (selectedLevel > score.levelsCompleted()+1 && selectedLevel <= 6)
                System.out.println("          \u001B[31m" + "You should complete level " + (score.levelsCompleted()+1) + " first!" + "\u001B[0m");
            else
                System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
        }
    }
}
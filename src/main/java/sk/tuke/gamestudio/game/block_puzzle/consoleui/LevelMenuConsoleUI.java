package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Color;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.game.block_puzzle.levels.Level;
import main.java.sk.tuke.gamestudio.service.LevelService;
import main.java.sk.tuke.gamestudio.service.LevelServiceJDBC;

import java.util.Scanner;

public class LevelMenuConsoleUI {
    private final Field field;
    private boolean isLevelSelected;
    private final Scanner console;
    private Level level;
    private int selectedLevel;
    private User user;
    public LevelMenuConsoleUI(Field field) {
        this.field = field;
        isLevelSelected = false;
        console = new Scanner(System.in);
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void generateLevelMenu() {
        for (int i = 1; i < 4; i++) {
            field.getMap()[3][i] = "|";
            field.getMap()[14][i] = "|";
            field.getMap()[19][i] = "|";
            field.getMap()[30][i] = "|";
            field.getMap()[35][i] = "|";
            field.getMap()[46][i] = "|";

            field.getMap()[3][i+5] = "|";
            field.getMap()[14][i+5] = "|";
            field.getMap()[19][i+5] = "|";
            field.getMap()[30][i+5] = "|";
            field.getMap()[35][i+5] = "|";
            field.getMap()[46][i+5] = "|";
        }

        for (int i = 4; i < 14; i++) {
            field.getMap()[i][1] = "‾";
            field.getMap()[i][6] = "‾";
            field.getMap()[i][3] = "_";
            field.getMap()[i][8] = "_";

            field.getMap()[i+16][1] = "‾";
            field.getMap()[i+16][6] = "‾";
            field.getMap()[i+16][3] = "_";
            field.getMap()[i+16][8] = "_";

            field.getMap()[i+32][1] = "‾";
            field.getMap()[i+32][6] = "‾";
            field.getMap()[i+32][3] = "_";
            field.getMap()[i+32][8] = "_";
        }

        for (int i = 6, j = 1; i < 39; i+= 16, j++) {
            field.getMap()[i][2] = "L";
            field.getMap()[i+1][2] = "e";
            field.getMap()[i+2][2] = "v";
            field.getMap()[i+3][2] = "e";
            field.getMap()[i+4][2] = "l";
            field.getMap()[i+5][2] = String.valueOf(j);
        }

        for (int i = 6, j = 4; i < 39; i+= 16, j++) {
            field.getMap()[i][7] = "L";
            field.getMap()[i+1][7] = "e";
            field.getMap()[i+2][7] = "v";
            field.getMap()[i+3][7] = "e";
            field.getMap()[i+4][7] = "l";
            field.getMap()[i+5][7] = String.valueOf(j);
        }
        markLevels();
    }
    private void markLevels() {
        for (int a = 3, b = 14, j = 0; a <= 36; a += 16, b += 16, j++) {
            String color = user.getLevelsCompleted() > j ? Color.GREEN_BACKGROUND :
                    user.getLevelsCompleted() == j ? Color.YELLOW_BACKGROUND : Color.BLACK_BACKGROUND;

            field.getMap()[a][1] = color + field.getMap()[a][1];
            field.getMap()[a][2] = color + field.getMap()[a][2];
            field.getMap()[a][3] = color + field.getMap()[a][3];
            field.getMap()[b][1] += Color.RESET;
            field.getMap()[b][2] += Color.RESET;
            field.getMap()[b][3] += Color.RESET;
        }

        for (int a = 3, b = 14, j = 3; a <= 36; a += 16, b += 16, j++) {
            String color = user.getLevelsCompleted() >= j ? Color.GREEN_BACKGROUND : Color.BLACK_BACKGROUND;
            field.getMap()[a][6] = color + field.getMap()[a][6];
            field.getMap()[a][7] = color + field.getMap()[a][7];
            field.getMap()[a][8] = color + field.getMap()[a][8];
            field.getMap()[b][6] += Color.RESET;
            field.getMap()[b][7] += Color.RESET;
            field.getMap()[b][8] += Color.RESET;
        }
    }
    public boolean isLevelSelected() {
        return isLevelSelected;
    }
    public Level getLevel() {
        return level;
    }
    public void parseInput() {
        System.out.print("Choose level: ");
        String command = console.nextLine().trim().toUpperCase();
        if (command.equals("E"))
            System.exit(0);

        try {
            selectedLevel = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
            return;
        }

        if (command.matches("([1-" + (user.getLevelsCompleted()+1) + "])")) {
            LevelService levelService = new LevelServiceJDBC();
            level = levelService.getLevelJDBC(selectedLevel, field);
            isLevelSelected = true;
        }
        else {
            if (selectedLevel > user.getLevelsCompleted()+1 && selectedLevel <= 6)
                System.out.println("          \u001B[31m" + "You should complete level " + (user.getLevelsCompleted()+1) + " first!" + "\u001B[0m");
            else
                System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
        }
    }
}

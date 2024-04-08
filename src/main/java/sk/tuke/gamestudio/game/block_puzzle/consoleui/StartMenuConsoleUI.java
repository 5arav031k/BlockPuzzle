package sk.tuke.gamestudio.game.block_puzzle.consoleui;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.service.*;

import java.util.Scanner;

public class StartMenuConsoleUI {
    @Autowired
    private Field field;
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;
    private final Scanner console;
    private boolean isUserLogIn;
    @Getter
    private Score score;
    @Getter
    private User user;

    public StartMenuConsoleUI() {
        console = new Scanner(System.in);
        isUserLogIn = false;
    }

    public boolean isUserLogIn() {
        return isUserLogIn;
    }

    public void generateLogInPrompt() {
        String[] login = "(1) Log In".split("");
        for (int row = 19; row <= 28; row++) {
            field.getMap()[row][2].setValue(login[row-19]);
        }
        field.getMap()[19][2].setValue("\u001B[36m(");
        field.getMap()[28][2].setValue("n\u001B[0m");

        String[] createAccount = "(2) Create account".split("");
        for (int row = 16; row <= 33; row++) {
            field.getMap()[row][8].setValue(createAccount[row-16]);
        }
        field.getMap()[16][8].setValue("\u001B[36m(");
        field.getMap()[33][8].setValue("t\u001B[0m");

        generateGameName();
    }

    private void generateGameName() {
        String[] title1 = "╔═══♪✩♫═══╗                    ╔═══♪✩♫═══╗".split("");
        String[] title2 = "║ ●  ♚  ● ║    Block Puzzle    ║ ●  ♚  ● ║".split("");
        String[] title3 = "╚═══►❃◄═══╝                    ╚═══►❃◄═══╝".split("");

        for (int row = 0; row < title2.length; row++) {
            field.getMap()[row+3][4].setValue(title1[row]);
            field.getMap()[row+3][5].setValue(title2[row]);
            field.getMap()[row+3][6].setValue(title3[row]);
        }
        field.getMap()[3][4].setValue("\u001B[32m╔");
        field.getMap()[3][5].setValue("\u001B[32m║");
        field.getMap()[3][6].setValue("\u001B[32m╚");

        field.getMap()[18][5].setValue("\u001B[31mB");
        field.getMap()[29][5].setValue("e\u001B[0m");
        field.getMap()[34][5].setValue("\u001B[32m║");

        field.getMap()[title2.length+4][4].setValue("\u001B[0m");
        field.getMap()[title2.length+4][5].setValue("\u001B[0m");
        field.getMap()[title2.length+4][6].setValue("\u001B[0m");
    }

    public void parseInput() {
        System.out.print("Choose 1 or 2: ");
        String command = console.nextLine().trim().toUpperCase();
        if (command.equals("EXIT"))
            System.exit(0);

        if (command.matches("([1-2])")) {
            String login = getLogin();
            String password = getPassword();
            if (command.equals("1")) {
                try {
                    user = userService.logIn(login, password);
                } catch (Exception e) {
                    System.out.println("           \u001B[31m" + "Bad login or password!" + "\u001B[0m");
                }
            }
            else {
                try {
                    user = userService.addUser(login, password);
                } catch (Exception e) {
                    System.out.println("           \u001B[31m" + "This login is already taken!" + "\u001B[0m");
                }
                if (user != null)
                    scoreService.addScore(user.getLogin());
            }
            if (user != null) {
                try {
                    score = scoreService.getScore(user.getLogin());
                } catch (Exception e) {
                    System.out.println("           \u001B[31m" + "Bad username!" + "\u001B[0m");
                }
            }

            isUserLogIn = user != null;
        }
        else {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
        }
    }

    private String getLogin() {
        System.out.print("Enter login (min 3 characters): ");
        String login = console.nextLine();
        while (login.length() < 3) {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
            System.out.print("Enter login (min 3 characters): ");
            login = console.nextLine();
        }
        return login;
    }

    private String getPassword() {
        System.out.print("Enter password (min 6 characters): ");
        String password = console.nextLine();
        while (password.length() < 6) {
            System.out.println("          \u001B[31m" + "Bad input!" + "\u001B[0m");
            System.out.print("Enter password (min 6 characters): ");
            password = console.nextLine();
        }
        return password;
    }
}
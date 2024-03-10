package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.service.ScoreService;
import main.java.sk.tuke.gamestudio.service.ScoreServiceJDBC;
import main.java.sk.tuke.gamestudio.service.UserService;
import main.java.sk.tuke.gamestudio.service.UserServiceJDBC;

import java.util.Scanner;

public class StartMenuConsoleUI {
    private final Field field;
    private final Scanner console;
    private boolean isUserLogIn;
    private Score score;

    public StartMenuConsoleUI(Field field) {
        this.field = field;
        console = new Scanner(System.in);
        isUserLogIn = false;
    }

    public Score getScore() {
        return score;
    }

    public boolean isUserLogIn() {
        return isUserLogIn;
    }
    public void generateLogInPrompt() {
        String[] login = "(1) Log In".split("");
        for (int i = 19; i <= 28; i++) {
            field.getMap()[i][2].setValue(login[i-19]);
        }
        field.getMap()[19][2].setValue("\u001B[36m(");
        field.getMap()[28][2].setValue("n\u001B[0m");

        String[] createAccount = "(2) Create account".split("");
        for (int i = 16; i <= 33; i++) {
            field.getMap()[i][8].setValue(createAccount[i-16]);
        }
        field.getMap()[16][8].setValue("\u001B[36m(");
        field.getMap()[33][8].setValue("t\u001B[0m");

        generateGameName();
    }
    private void generateGameName() {
        String[] title1 = "╔═══♪✩♫═══╗                    ╔═══♪✩♫═══╗".split("");
        String[] title2 = "║ ●  ♚  ● ║    Block Puzzle    ║ ●  ♚  ● ║".split("");
        String[] title3 = "╚═══►❃◄═══╝                    ╚═══►❃◄═══╝".split("");

        for (int i = 0; i < title2.length; i++) {
            field.getMap()[i+3][4].setValue(title1[i]);
            field.getMap()[i+3][5].setValue(title2[i]);
            field.getMap()[i+3][6].setValue(title3[i]);
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
        if (command.equals("E"))
            System.exit(0);

        UserService userService = new UserServiceJDBC();
        ScoreService scoreService = new ScoreServiceJDBC();
        User user;

        if (command.matches("([1-2])")) {
            String login = getLogin();
            String password = getPassword();
            if (command.equals("1")) {
                user = userService.logIn(login, password);
                score = scoreService.getScore(user);
            }
            else {
                user = userService.addUser(login, password);
                score = scoreService.addScore(user);
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

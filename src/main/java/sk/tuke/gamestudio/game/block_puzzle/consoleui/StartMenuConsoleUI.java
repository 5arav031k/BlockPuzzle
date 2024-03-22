package sk.tuke.gamestudio.game.block_puzzle.consoleui;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;
import sk.tuke.gamestudio.service.UserService;
import sk.tuke.gamestudio.service.UserServiceJDBC;

import java.util.Scanner;

public class StartMenuConsoleUI {
    private final Field field;
    private final Scanner console;
    private boolean isUserLogIn;
    private Score score;
    private User user;

    public StartMenuConsoleUI(Field field) {
        this.field = field;
        console = new Scanner(System.in);
        isUserLogIn = false;
    }

    public Score getScore() {
        return score;
    }
    public User getUser() {
        return user;
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

        UserService userService = new UserServiceJDBC();
        ScoreService scoreService = new ScoreServiceJDBC();

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
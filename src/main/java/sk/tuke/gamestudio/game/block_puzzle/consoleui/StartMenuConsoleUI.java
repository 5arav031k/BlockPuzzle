package main.java.sk.tuke.gamestudio.game.block_puzzle.consoleui;

import main.java.sk.tuke.gamestudio.entity.User;
import main.java.sk.tuke.gamestudio.game.block_puzzle.core.Field;
import main.java.sk.tuke.gamestudio.service.UserService;
import main.java.sk.tuke.gamestudio.service.UserServiceJDBC;

import java.util.Scanner;

public class StartMenuConsoleUI {
    private final Field field;
    private final Scanner console;
    private boolean isUserLogIn;
    private User user;

    public StartMenuConsoleUI(Field field) {
        this.field = field;
        console = new Scanner(System.in);
        isUserLogIn = false;
    }

    public User getUser() {
        return user;
    }
    public boolean isUserLogIn() {
        return isUserLogIn;
    }
    public void generateLogInPrompt() {
        String[] login = "(1) Log In".split("");
        for (int i = 15; i <= 24; i++) {
            field.getMap()[i][2].setValue(login[i-15]);
        }

        String[] createAccount = "(2) Create account".split("");
        for (int i = 15; i <= 32; i++) {
            field.getMap()[i][7].setValue(createAccount[i-15]);
        }
    }
    public void parseInput() {
        System.out.print("Choose 1 or 2: ");
        String command = console.nextLine().trim().toUpperCase();
        if (command.equals("E"))
            System.exit(0);

        UserService userService = new UserServiceJDBC();
        if (command.matches("([1-2])")) {
            String login = getLogin();
            String password = getPassword();
            user = command.equals("1") ? userService.logIn(login, password) : userService.addUser(login, password);

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

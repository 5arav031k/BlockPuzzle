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
        field.getMap()[15][2] = "(";
        field.getMap()[16][2] = "1";
        field.getMap()[17][2] = ")";

        field.getMap()[19][2] = "L";
        field.getMap()[20][2] = "o";
        field.getMap()[21][2] = "g";
        field.getMap()[23][2] = "I";
        field.getMap()[24][2] = "n";

        field.getMap()[15][7] = "(";
        field.getMap()[16][7] = "2";
        field.getMap()[17][7] = ")";

        field.getMap()[19][7] = "C";
        field.getMap()[20][7] = "r";
        field.getMap()[21][7] = "e";
        field.getMap()[22][7] = "a";
        field.getMap()[23][7] = "t";
        field.getMap()[24][7] = "e";
        field.getMap()[26][7] = "a";
        field.getMap()[27][7] = "c";
        field.getMap()[28][7] = "c";
        field.getMap()[29][7] = "o";
        field.getMap()[30][7] = "u";
        field.getMap()[31][7] = "n";
        field.getMap()[32][7] = "t";
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

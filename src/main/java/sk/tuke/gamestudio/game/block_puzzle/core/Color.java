package sk.tuke.gamestudio.game.block_puzzle.core;

public class Color {
    public static final String RESET = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m" + "█" + RESET;
    public static final String COLOR_GREEN = "\u001B[32m" + "█" + RESET;
    public static final String COLOR_YELLOW = "\u001B[33m" + "█" + RESET;
    public static final String COLOR_BLUE = "\u001B[34m" + "█" + RESET;
    public static final String COLOR_PURPLE = "\u001B[35m" + "█" + RESET;
    public static final String COLOR_CYAN = "\u001B[36m" + "█" + RESET;
    public static final String COLOR_GRAY = "\u001B[37m" + "█" + RESET;
    public static final String MARKED = "\u001B[31m" + "X" + RESET;
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";

    public static String getColorByString(String color) {
        return switch (color) {
            case "RED" -> COLOR_RED;
            case "GREEN" -> COLOR_GREEN;
            case "YELLOW" -> COLOR_YELLOW;
            case "BLUE" -> COLOR_BLUE;
            case "PURPLE" -> COLOR_PURPLE;
            case "CYAN" -> COLOR_CYAN;
            case "GRAY" -> COLOR_GRAY;
            default -> null;
        };
    }

    public static String getStringByColor(String color) {
        return switch (color) {
            case COLOR_RED -> "RED";
            case COLOR_GREEN -> "GREEN";
            case COLOR_YELLOW -> "YELLOW";
            case COLOR_BLUE -> "BLUE";
            case COLOR_PURPLE -> "PURPLE";
            case COLOR_CYAN -> "CYAN";
            case COLOR_GRAY -> "GRAY";
            default -> null;
        };
    }
}

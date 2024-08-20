package sk.tuke.gamestudio.game.block_puzzle.consoleui;

import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ExceptionConstants;

class GameStudioExceptionHandler {

    public static void handleException(GameStudioException e) {
        switch (e.getMessage()) {
            case ExceptionConstants.BAD_LOGIN_OR_PASSWORD,
                 ExceptionConstants.LOGIN_ALREADY_TAKEN,
                 ExceptionConstants.BAD_INPUT
                    -> printError(e.getMessage());
        }
    }

    public static void printError(String message) {
        System.out.println("           \u001B[31m" + message + "\u001B[0m");
    }
}

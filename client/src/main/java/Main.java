import chess.*;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;
import static ui.ServerFacade.*;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println(SET_TEXT_COLOR_WHITE + "♕ 240 Chess Client: " + piece);
        System.out.print("Welcome to the Chess App! Type Help to get started. \n>>>");
        PreLogin();
    }

    private static void PreLogin() {
        boolean stillPlaying = true;
        while (stillPlaying) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var args = line.split(" ");
            switch (args[0]) {
                case "help" -> PreLoginHelp();
                case "register" -> {
                    try {
                        Register(args);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "login" -> {
                    try {
                        String authToken = Login(args);
                        PostLogin(args[1], authToken);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "quit" -> stillPlaying = false;
                case null, default -> System.out.println("Not a valid command. Type Help for a list of commands.");
            }
        }
    }

    private static void PostLogin(String username, String authToken) {
        System.out.println(ERASE_SCREEN + "Welcome " + username + " to Chess Mania! Type help for a list of commands.");
        boolean stillPlaying = true;
        while (stillPlaying) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var args = line.split(" ");
            switch (args[0]) {
                case "create" -> {
                    try {
                        CreateGame(args, authToken);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "list" -> {
                    try {
                        ListGames(authToken);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "join" -> {
                    try {
                        JoinGame(args, authToken);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "observe" -> System.out.println("Observe a game dude");
                case "logout" -> stillPlaying = false; //Don't forget to call logout API
                case "quit" -> System.exit(0);
                case "help" -> PostLoginHelp();
                case null, default -> System.out.println("Not a valid command. Type help for a list of commands.");
            }
        }
    }

    private static void PreLoginHelp() {
        String blue = SET_TEXT_COLOR_BLUE;
        String grey = SET_TEXT_COLOR_LIGHT_GREY;
        System.out.println(blue + "register <USERNAME> <PASSWORD> <EMAIL>" + grey + " - to create an account");
        System.out.println(blue + "login <USERNAME> <PASSWORD>" + grey + " - to play chess");
        System.out.println(blue + "quit" + grey + " - to leave");
        System.out.print(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE + "\n>>>");
    }

    private static void PostLoginHelp() {
        String blue = SET_TEXT_COLOR_BLUE;
        String grey = SET_TEXT_COLOR_LIGHT_GREY;
        System.out.println(blue + "create <NAME>" + grey + " - a chess game");
        System.out.println(blue + "list" + grey + " - show all available games");
        System.out.println(blue + "join <ID> [WHITE|BLACK|<empty>]" + grey + " - a chess game");
        System.out.println(blue + "observe <ID>" + grey + " - a chess game");
        System.out.println(blue + "logout" + grey + " - when you are done");
        System.out.println(blue + "quit" + grey + " - playing chess");
        System.out.println(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE + "\n>>>");
    }
}
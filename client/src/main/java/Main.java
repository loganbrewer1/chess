import chess.*;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;
import static ui.ServerFacade.*;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println(SET_TEXT_COLOR_WHITE + "♕ 240 Chess Client: " + piece);
        System.out.print("Welcome to the Chess App! Type Help to get started.");
        PreLogin();
    }

    private static void PreLogin() {
        boolean stillPlaying = true;
        while (stillPlaying) {
            System.out.print("\n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var args = line.split(" ");
            switch (args[0]) {
                case "help" -> PreLoginHelp();
                case "register" -> {
                    try {
                        Register(args);
                    } catch (Exception e) {
                        if (e.getMessage().contains("400")) {
                            System.out.println("Something in your register request was off. Try double checking your input.");
                        } else if (e.getMessage().contains("403")) {
                            System.out.println("The player color is already taken");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
                    }
                }
                case "login" -> {
                    try {
                        String authToken = Login(args);
                        PostLogin(args[1], authToken);
                    } catch (Exception e) {
                        if (e.getMessage().contains("401")) {
                            System.out.println("Wrong username and password");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
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
            System.out.print("\n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var args = line.split(" ");
            switch (args[0]) {
                case "create" -> {
                    try {
                        CreateGame(args, authToken);
                    } catch (Exception e) {
                        if (e.getMessage().contains("400")) {
                            System.out.println("Something in your join request was off. Try double checking the gameID.");
                        } else if (e.getMessage().contains("401")) {
                            System.out.println("You are unauthorized to make this request");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
                    }
                }
                case "list" -> {
                    try {
                        ListGames(authToken);
                    } catch (Exception e) {
                        if (e.getMessage().contains("401")) {
                            System.out.println("You are unauthorized to make this request");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
                    }
                }
                case "join", "observe" -> {
                    try {
                        JoinGame(args, authToken);
                    } catch (Exception e) {
                        if (e.getMessage().contains("400")) {
                            System.out.println("Something in your join request was off. Try double checking the gameID.");
                        } else if (e.getMessage().contains("401")) {
                            System.out.println("You are unauthorized to make this request");
                        } else if (e.getMessage().contains("403")) {
                            System.out.println("The player color is already taken");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
                    }
                }
                case "logout" -> {
                    try {
                        Logout(authToken);
                        stillPlaying = false;
                    } catch (Exception e) {
                        if (e.getMessage().contains("401")) {
                            System.out.println("You are unauthorized to make this request");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        }
                    }
                }
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
        System.out.print(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE);
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
        System.out.println(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE);
    }
}
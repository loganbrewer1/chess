package ui;

import model.GameData;

import java.util.Objects;
import java.util.Scanner;

import static ui.BoardUI.PrintBoardBlack;
import static ui.BoardUI.PrintBoardWhite;
import static ui.EscapeSequences.*;
import static ui.ServerFacade.*;

public class ConsoleUI {
    public static void PreLogin() {
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
                        if (args.length != 4) {
                            System.out.println("Too many or too few fields were given. Try again.");
                        } else {
                            String authToken = Register(args);
                            PostLogin(args[1], authToken);
                        }
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
                        if (args.length != 3) {
                            System.out.println("Too many or too few fields were given. Try again.");
                        } else {
                            String authToken = Login(args);
                            PostLogin(args[1], authToken);
                        }
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
        System.out.print(ERASE_SCREEN + "Welcome " + username + " to Chess Mania! Type help for a list of commands.");
        boolean stillPlaying = true;
        while (stillPlaying) {
            System.out.print(RESET_BG_COLOR + "\n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var args = line.split(" ");
            switch (args[0]) {
                case "create" -> {
                    try {
                        if (args.length != 2) {
                            System.out.println("Too many or too few fields were given. Try again.");
                        } else {
                            CreateGame(args, authToken);
                        }
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
                        if (args.length == 2 || args.length == 3) {
                            JoinGame(args, authToken);
                            PostJoinGame(args, authToken);
                        } else {
                            System.out.println("Too many or too few fields were given. Try again.");
                        }
                    } catch (Exception e) {
                        if (e.getMessage().contains("400")) {
                            System.out.println("Something in your join request was off. Try double checking the gameID.");
                        } else if (e.getMessage().contains("401")) {
                            System.out.println("You are unauthorized to make this request");
                        } else if (e.getMessage().contains("403")) {
                            System.out.println("The player color is already taken");
                        } else if (e.getMessage().contains("500")) {
                            System.out.println("There was a server error.");
                        } else {
                            System.out.println(e.getMessage());
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
                case null, default -> System.out.print("Not a valid command. Type help for a list of commands.");
            }
        }
    }

    private static void PostJoinGame(String[] args, String authToken) {
        try {
            GameData gameData = GetGame(authToken, args[1]);
            PrintRespectiveBoard(args, gameData);
            System.out.println("You successfully joined the match. Type help for a list of commands.");
            boolean stillPlaying = true;
            while (stillPlaying) {
                System.out.print("\n>>> ");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                var answerArray = line.split(" ");
                switch (answerArray[0]) {
                    case "redraw" -> PrintRespectiveBoard(args, GetGame(authToken, args[1]));
                    case "leave" -> stillPlaying = false;
                    case "move" -> System.out.println("Input your move");
                    case "resign" -> System.out.println("You have resigned");
                    case "highlight" -> System.out.println("Here are your highlighted moves");
                    case "help" -> PostJoinHelp();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void PrintRespectiveBoard(String[] args, GameData gameData) {
        System.out.println(ERASE_SCREEN);
        if (args.length == 3) {
            if (Objects.equals(args[2], "WHITE")) {
                PrintBoardWhite(gameData.game().getBoard());
            } else if (Objects.equals(args[2], "BLACK")) {
                PrintBoardBlack(gameData.game().getBoard());
            } else {
                System.out.println(args[2] + " is not a Chess player color. What is wrong with you??");
            }
        } else {
            PrintBoardWhite(gameData.game().getBoard());
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
        System.out.print(ERASE_SCREEN);
        System.out.println(blue + "create <NAME>" + grey + " - a chess game");
        System.out.println(blue + "list" + grey + " - show all available games");
        System.out.println(blue + "join <ID> [WHITE|BLACK|<empty>]" + grey + " - a chess game");
        System.out.println(blue + "observe <ID>" + grey + " - a chess game");
        System.out.println(blue + "logout" + grey + " - when you are done");
        System.out.println(blue + "quit" + grey + " - playing chess");
        System.out.print(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE);
    }

    private static void PostJoinHelp() {
        String blue = SET_TEXT_COLOR_BLUE;
        String grey = SET_TEXT_COLOR_LIGHT_GREY;
        System.out.print(ERASE_SCREEN);
        System.out.println(blue + "redraw " + grey + " - Chess Board");
        System.out.println(blue + "leave" + grey + " - the chess match");
        System.out.println(blue + "move <START POSITION> <END POSITION>" + grey + " - make your next move (eg. move e2 e4)");
        System.out.println(blue + "resign" + grey + " - the chess match");
        System.out.println(blue + "highlight <PIECE POSITION>" + grey + " - legal moves (eg. highlight e2)");
        System.out.print(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE);
    }
}

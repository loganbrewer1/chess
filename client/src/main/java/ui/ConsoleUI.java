package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.GameData;
import webSocketMessages.userCommands.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import static ui.BoardUI.*;
import static ui.EscapeSequences.*;
import static ui.ServerFacade.*;

public class ConsoleUI {
    public static boolean playingBlack = false;
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
                        if (args.length == 2) {
                            JoinGame(args, authToken);
                            System.out.println(GetGame(authToken, args[1]).whiteUsername());
                            SendJoinObserver(authToken, Integer.valueOf(args[1]), username);
                            PostJoinGame(args, authToken, username);
                        } else if (args.length == 3) {
                            JoinGame(args, authToken);
                            if (args[2].equalsIgnoreCase("black")) {
                                playingBlack = true;
                                SendJoinPlayer(authToken, Integer.valueOf(args[1]), ChessGame.TeamColor.BLACK ,username);
                            } else if (args[2].equalsIgnoreCase("white")) {
                                SendJoinPlayer(authToken, Integer.valueOf(args[1]), ChessGame.TeamColor.WHITE ,username);
                            }
                            PostJoinGame(args, authToken, username);
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

    private static void PostJoinGame(String[] args, String authToken, String username) {
        try {
            GameData gameData = GetGame(authToken, args[1]);
            Thread.sleep(100);
            System.out.println("You successfully joined the match. Type help for a list of commands.");

            boolean stillPlaying = true;
            while (stillPlaying) {
                System.out.print("\n");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                var answerArray = line.split(" ");
                switch (answerArray[0]) {
                    case "redraw" -> PrintRespectiveBoard(args, GetGame(authToken, args[1]));
                    case "leave" -> {
                        stillPlaying = false;
                        playingBlack = false;
                        SendLeaveCommand(authToken, gameData.gameID(), username);
                    }
                    case "move" -> {
                        ChessMove clientMove = ParseChessMove(answerArray);
                        SendMakeMove(authToken, gameData.gameID(), clientMove, username);
                    }
                    case "resign" -> SendResignCommand(authToken, gameData.gameID(), username);
                    case "highlight" -> HighlightLegalMoves(answerArray, GetGame(authToken, args[1]));
                    case "help" -> PostJoinHelp();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void HighlightLegalMoves(String[] args, GameData gameData) {
        ChessPosition pieceOfInterest = ConvertedPosition(args[1]);
        Collection<ChessMove> legalMoves = gameData.game().validMoves(pieceOfInterest);
        if (playingBlack) {
            HighlightMovesBlack(gameData.game().getBoard(), legalMoves);
        } else {
            HighlightMovesWhite(gameData.game().getBoard(), legalMoves);
        }
    }

    private static ChessMove ParseChessMove(String[] answerArray) {
        if (answerArray.length == 4) {
            return new ChessMove(ConvertedPosition(answerArray[1]), ConvertedPosition(answerArray[2]), ConvertPromotion(answerArray[3]));
        } else {
            return new ChessMove(ConvertedPosition(answerArray[1]), ConvertedPosition(answerArray[2]), null);
        }
    }

    public static void PrintRespectiveBoard(String[] args, GameData gameData) {
        System.out.println(ERASE_SCREEN);
        if (args.length == 3) {
            if (Objects.equals(args[2].toLowerCase(), "white")) {
                PrintBoardWhite(gameData.game().getBoard());
            } else if (Objects.equals(args[2].toLowerCase(), "black")) {
                PrintBoardBlack(gameData.game().getBoard());
                playingBlack = true;
            } else {
                System.out.println(args[2] + " is not a Chess player color. What is wrong with you??");
            }
        } else {
            PrintBoardWhite(gameData.game().getBoard());
        }
    }

    public static void SendMakeMove(String authToken, int gameID, ChessMove move, String username) {
        try {
            String makeMoveJson = new Gson().toJson(new MakeMove(authToken, gameID, move, username));
            new WSClient().send(makeMoveJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("You sent a move (client)");
    }

    public static void SendJoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor, String username) {
        try {
            String joinPlayerJson = new Gson().toJson(new JoinPlayer(authToken, gameID, playerColor, username));
            new WSClient().send(joinPlayerJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("You joined as a player (client)");
    }

    public static void SendJoinObserver(String authToken, Integer gameID, String username) {
        try {
            String joinObserverJson = new Gson().toJson(new JoinObserver(authToken, gameID, username));
            new WSClient().send(joinObserverJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("You joined as an observer (client)");
    }

    public static void SendResignCommand(String authToken, Integer gameID, String username) {
        try {
            String resignJson = new Gson().toJson(new Resign(authToken, gameID, username));
            new WSClient().send(resignJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("You resigned (client)");
    }

    public static void SendLeaveCommand(String authToken, int gameID, String username) {
        try {
            String leaveJson = new Gson().toJson(new Leave(authToken, gameID, username));
            new WSClient().send(leaveJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("You left (client)");
    }

    private static ChessPosition ConvertedPosition(String position) {
        char column = position.charAt(0);
        int row = Character.getNumericValue(position.charAt(1));
        int file = 10;

        switch (column) {
            case 'a' -> file = 1;
            case 'b' -> file = 2;
            case 'c' -> file = 3;
            case 'd' -> file = 4;
            case 'e' -> file = 5;
            case 'f' -> file = 6;
            case 'g' -> file = 7;
            case 'h' -> file = 8;
        }

        return new ChessPosition(row, file);
    }

    private static ChessPiece.PieceType ConvertPromotion(String pieceInput) {
        ChessPiece.PieceType pieceType = null;

        switch (pieceInput) {
            case "Q" -> pieceType = ChessPiece.PieceType.QUEEN;
            case "N" -> pieceType = ChessPiece.PieceType.KNIGHT;
            case "B" -> pieceType = ChessPiece.PieceType.BISHOP;
            case "R" -> pieceType = ChessPiece.PieceType.ROOK;
        }

        return pieceType;
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
        System.out.println(blue + "move <START POSITION> <END POSITION> <PROMOTION PIECE>" + grey + " - make your next move (eg. move e7 e8 Q)");
        System.out.println(blue + "resign" + grey + " - the chess match");
        System.out.println(blue + "highlight <PIECE POSITION>" + grey + " - legal moves (eg. highlight e2)");
        System.out.print(blue + "help" + grey + " - for a list of helpful commands" + SET_TEXT_COLOR_WHITE);
    }
}

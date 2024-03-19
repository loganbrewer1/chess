import chess.*;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;


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
            switch (line) {
                case "help" -> PreLoginHelp();
                case "register" -> System.out.println("Here are instructions on registering");
                case "login" -> System.out.println("This is how you login");
                case "quit" -> stillPlaying = false;
                case null, default -> System.out.println("Not a valid command. Type Help for a list of commands.");
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
}
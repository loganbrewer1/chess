import chess.*;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        System.out.printf("Welcome to the Chess App! Type Help to get started. \n>>>");
        PreLogin();
    }

    private static void PreLogin() {
        boolean stillPlaying = true;
        while (stillPlaying) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (Objects.equals(line, "Help")) {
                System.out.print("Here is how I can help you\n");
            } else if (Objects.equals(line, "Register")) {
                System.out.print("Here are instructions on registering\n");
            } else if (Objects.equals(line, "Login")) {
                System.out.print("This is how you login\n");
            } else if (Objects.equals(line, "Quit")) {
                stillPlaying = false;
            } else {
                System.out.print("Not a valid command. Type Help for a list of commands.\n");
            }
        }

    }
}
import chess.*;

import static ui.ConsoleUI.PreLogin;
import static ui.EscapeSequences.*;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println(SET_TEXT_COLOR_WHITE + "♕ 240 Chess Client: " + piece);
        System.out.print("Welcome to the Chess App! Type Help to get started.");
        PreLogin();
    }
}
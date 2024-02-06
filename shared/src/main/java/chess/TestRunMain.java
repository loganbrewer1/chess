package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.pieces.KnightMovesCalculator;
import chess.pieces.BishopMovesCalculator;
import chess.pieces.RookMovesCalculator;
import chess.pieces.KingMovesCalculator;
import chess.pieces.QueenMovesCalculator;
import chess.pieces.PawnMovesCalculator;

public class TestRunMain {
    public static void main(String[] args) {
        ChessGame myGame = new ChessGame();

        System.out.println("Before move");
        myGame.getBoard().addPiece(new ChessPosition(6,2),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        myGame.getBoard().printBoard();

        ChessPosition startPosition = new ChessPosition(1,1);
        ChessPosition endPosition = new ChessPosition(4,1);

        try {
            myGame.makeMove(new ChessMove(startPosition, endPosition, null));
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("After move");
        myGame.getBoard().printBoard();
    }
}
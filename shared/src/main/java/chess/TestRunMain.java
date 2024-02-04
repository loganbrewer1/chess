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

        myGame.getBoard().printBoard();
        ChessPosition startPosition = new ChessPosition(2,4);
        ChessPosition endPosition = new ChessPosition(4,4);

        try {
            myGame.makeMove(new ChessMove(startPosition, endPosition, null));
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        }

    }
}
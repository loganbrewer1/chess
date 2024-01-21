package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.pieces.KnightMovesCalculator;
import chess.pieces.BishopMovesCalculator;

public class TestRunMain {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        chessBoard.addPiece(new ChessPosition(4, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        chessBoard.printBoard();

        // Testing KnightMovesCalculator
        ChessPosition bishopPosition = new ChessPosition(4, 4); // Choose a position for the bishop
        BishopMovesCalculator bishopCalculator = new BishopMovesCalculator();
        Collection<ChessMove> bishopMoves = bishopCalculator.pieceMoves(chessBoard, bishopPosition);

        System.out.println("Valid moves for the bishop at position " + bishopPosition + ":");
        for (ChessMove move : bishopMoves) {
            System.out.println(move.getEndPosition());
        }
    }
}
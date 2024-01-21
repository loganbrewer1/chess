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
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        chessBoard.addPiece(new ChessPosition(4, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        chessBoard.printBoard();

        // Testing KnightMovesCalculator
        ChessPosition piecePosition = new ChessPosition(4, 4); // Choose a position for the bishop
        RookMovesCalculator calc = new RookMovesCalculator();
        Collection<ChessMove> bishopMoves = calc.pieceMoves(chessBoard, piecePosition);

        System.out.println("Valid moves for the bishop at position " + piecePosition + ":");
        for (ChessMove move : bishopMoves) {
            System.out.println(move.getEndPosition());
        }
    }
}
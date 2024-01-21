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

        chessBoard.addPiece(new ChessPosition(3, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        chessBoard.printBoard();

        ChessPosition piecePosition = new ChessPosition(3, 4); // Choose a position for the bishop
        KingMovesCalculator calc = new KingMovesCalculator();
        Collection<ChessMove> moveList = calc.pieceMoves(chessBoard, piecePosition);

        System.out.println("Valid moves for the piece at position " + piecePosition + ":");
        for (ChessMove move : moveList) {
            System.out.println(move.getEndPosition());
        }
    }
}
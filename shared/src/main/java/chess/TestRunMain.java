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

        chessBoard.addPiece(new ChessPosition(3, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        chessBoard.printBoard();

        ChessPiece myPawn = chessBoard.getPiece(new ChessPosition(3,4));

        for (ChessMove possibleMove : myPawn.pieceMoves(chessBoard,new ChessPosition(3,4))) {
                System.out.println(possibleMove.getEndPosition());
        }
    }
}
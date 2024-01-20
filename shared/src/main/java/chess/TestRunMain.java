package chess;

import java.util.ArrayList;
import java.util.Collection;
import chess.pieces.KnightMovesCalculator;

public class TestRunMain {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        chessBoard.printBoard();

        // Testing KnightMovesCalculator
        ChessPosition knightPosition = new ChessPosition(8, 2); // Choose a position for the knight
        KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
        Collection<ChessMove> knightMoves = knightMovesCalculator.pieceMoves(chessBoard, knightPosition);

        System.out.println("Valid moves for the knight at position " + knightPosition + ":");
        for (ChessMove move : knightMoves) {
            System.out.println(move.getEndPosition());
        }
    }
}


//package chess.pieces;
//
//import chess.ChessBoard;
//import chess.ChessMove;
//import chess.ChessPiece;
//import chess.ChessPosition;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class KnightMovesCalculator {
//
//    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        Collection<ChessMove> validMoves = new ArrayList<>();
//
//        int row = myPosition.getRow();
//        int col = myPosition.getColumn();
//
//        // Define all possible moves relative to the current position
//        int[][] possibleMoves = {
//                {-2, 1}, {-2, -1},
//                {2, 1}, {2, -1},
//                {1, 2}, {1, -2},
//                {-1, 2}, {-1, -2}
//        };
//
//        // Check each possible move and add valid moves to the collection
//        for (int[] move : possibleMoves) {
//            int newRow = row + move[0];
//            int newCol = col + move[1];
//
//            // Check if the new position is within the bounds of the board
//            if (isValidPosition(newRow, newCol)) {
//                ChessPosition newPosition = new ChessPosition(newRow, newCol);
//                ChessPiece myPiece = board.getPiece(myPosition);
//                ChessPiece targetPiece = board.getPiece(newPosition);
//
//                // Check if there is no piece at the target position or if it's an opposing team's piece
//                if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
//                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);
//                    validMoves.add(newMove);
//                }
//            }
//        }
//
//        return validMoves;
//    }
//
//    // Helper method to check if a position is within the bounds of the chessboard
//    private boolean isValidPosition(int row, int col) {
//        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
//    }
//}

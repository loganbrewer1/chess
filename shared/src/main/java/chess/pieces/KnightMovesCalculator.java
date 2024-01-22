package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();


        if (row - 2 >= 1 && row - 2 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row - 2, col + 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));
            }
        }
        if (row + 2 >= 1 && row + 2 <= 8 && col + 1 >= 1 && col + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row + 2, col + 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));
            }
        }
        if (row + 2 >= 1 && row + 2 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row + 2, col - 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));
            }
        }
        if (row - 2 >= 1 && row - 2 <= 8 && col - 1 >= 1 && col - 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row - 2, col - 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));
            }
        }
        if (row + 1 >= 1 && row + 1 <= 8 && col + 2 >= 1 && col + 2 <= 8) {
            ChessPosition newPosition = new ChessPosition(row + 1, col + 2);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));
            }
        }
        if (row + 1 >= 1 && row + 1 <= 8 && col - 2 >= 1 && col - 2 <= 8){
            ChessPosition newPosition = new ChessPosition(row + 1, col - 2);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));
            }
        }
        if (row - 1 >= 1 && row - 1 <= 8 && col + 2 >= 1 && col + 2 <= 8){
            ChessPosition newPosition = new ChessPosition(row - 1, col + 2);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));
            }
        }
        if (row - 1 >= 1 && row - 1 <= 8 && col - 2 >= 1 && col - 2 <= 8) {
            ChessPosition newPosition = new ChessPosition(row - 1, col - 2);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 2), null));
            }
        }
        return validMoves;
    }
}
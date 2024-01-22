package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        ChessPiece myKing = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();



        if (row + 1 <= 8 && col + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row + 1, col + 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
            }
        }
        if (col + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row, col + 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col + 1), null));
            }
        }
        if (row - 1 > 0 && col + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row - 1, col + 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null ||  myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));
            }
        }
        if (row - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(row - 1, col);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null ||  myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), null));
            }
        }
        if (row - 1 > 0 && col - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(row - 1, col - 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));
            }
        }
        if (col - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(row, col - 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col - 1), null));
            }
        }
        if (row + 1 <= 8 && col - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(row + 1, col - 1);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));
            }
        }
        if (row + 1 <= 8) {
            ChessPosition newPosition = new ChessPosition(row + 1, col);
            ChessPiece targetPiece = board.getPiece(newPosition);
            if (targetPiece == null || myKing.getTeamColor() != targetPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
            }
        }

        return validMoves;
    }
}

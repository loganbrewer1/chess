package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int i = 1; i <= 8; i++) {
            if (col + i <=8) {
                ChessPosition newPosition = new ChessPosition(row, col + i);
                ChessPiece targetPiece = board.getPiece(newPosition);
                if (targetPiece != null && targetPiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }
                if (targetPiece != null && targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col + i), null));
                    break;
                }
                else {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col + i), null));
                }
            }
        }

        for (int i = 1; i <= 8; i++) {
            if (col - i > 0) {
                ChessPosition newPosition = new ChessPosition(row, col - i);
                ChessPiece targetPiece = board.getPiece(newPosition);
                if (targetPiece != null && targetPiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }
                if (targetPiece != null && targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col - i), null));
                    break;
                }
                else {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col - i), null));
                }
            }
        }

        for (int i = 1; i <= 8; i++) {
            if (row - i > 0) {
                ChessPosition newPosition = new ChessPosition(row - i, col);
                ChessPiece targetPiece = board.getPiece(newPosition);
                if (targetPiece != null && targetPiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }
                if (targetPiece != null && targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row - i, col), null));
                    break;
                }
                else {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row - i, col), null));
                }
            }
        }

        for (int i = 1; i <= 8; i++) {
            if (row + i > 0) {
                ChessPosition newPosition = new ChessPosition(row + i, col);
                ChessPiece targetPiece = board.getPiece(newPosition);
                if (targetPiece != null && targetPiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }
                if (targetPiece != null && targetPiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row + i, col), null));
                    break;
                }
                else {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row + i, col), null));
                }
            }
        }

        return validMoves;
    }
}

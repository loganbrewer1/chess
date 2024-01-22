package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
                if (row == 2) {
                    if (board.getPiece(new ChessPosition(row + 2, col)) == null){
                        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
                    }
                }
            }
        }
        //If there are enemy pieces on a diagonal, those become valid moves.
        if (board.getPiece(new ChessPosition( row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
        }

        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
        if (row == 2) { //TODO Add logic for piece color
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
        }

        return validMoves;
    }
}

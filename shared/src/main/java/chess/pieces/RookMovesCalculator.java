package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        //TODO: Figure out logic for when a pieace is in the way
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int i = row + 1; i <= 8; i++) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(i, col), null));
        }
        for (int i = row - 1; i >= 1; i--) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(i, col), null));
        }

        // Rook can move horizontally
        for (int j = col + 1; j <= 8; j++) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row, j), null));
        }
        for (int j = col - 1; j >= 1; j--) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row, j), null));
        }

        return validMoves;
    }
}

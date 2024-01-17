package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
        if (row == 2) { //TODO Add logic for piece color
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
        }

        return validMoves;
    }
}

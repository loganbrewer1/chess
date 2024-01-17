package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 2), null));

        return validMoves;
    }
}

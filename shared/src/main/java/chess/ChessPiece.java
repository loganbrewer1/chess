package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    //TODO: HERE
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (type == PieceType.PAWN) {
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
            if (row == 2) { //TODO Add logic for piece color
                validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
            }
        };
        if (type == PieceType.KNIGHT) { //TODO: Add logic for moving off the board
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));
            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 2), null));
        }
        if (type == PieceType.ROOK) {
            
        }


        return validMoves;
    }
}

package chess;

import chess.pieces.BishopMovesCalculator;
import chess.pieces.RookMovesCalculator;
import chess.pieces.PawnMovesCalculator;
import chess.pieces.KnightMovesCalculator;
import chess.pieces.QueenMovesCalculator;
import chess.pieces.KingMovesCalculator;

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
    //TODO: This whole thing
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (type == PieceType.PAWN) {
            PawnMovesCalculator pawn = new PawnMovesCalculator();
            validMoves = pawn.pieceMoves(board, myPosition);
        };
        if (type == PieceType.KNIGHT) { //TODO: Add logic for moving off the board
            KnightMovesCalculator knight = new KnightMovesCalculator();
            validMoves = knight.pieceMoves(board, myPosition);
        }
        if (type == PieceType.ROOK) { //TODO: Figure out logic for when a pieace is in the way
            RookMovesCalculator rook = new RookMovesCalculator();
            validMoves = rook.pieceMoves(board, myPosition);
        }
        if (type == PieceType.BISHOP) { //TODO: Figure out logic for when a pieace is in the way
            BishopMovesCalculator bishop = new BishopMovesCalculator();
            validMoves = bishop.pieceMoves(board, myPosition);
        }
        if (type == PieceType.QUEEN) { //TODO: Figure out logic for when a pieace is in the way
            QueenMovesCalculator Queen = new QueenMovesCalculator();
            validMoves = Queen.pieceMoves(board, myPosition);
        }
        if (type == PieceType.KING) { //TODO: Figure out logic for when a pieace is in the way
            KingMovesCalculator king = new KingMovesCalculator();
            validMoves = king.pieceMoves(board, myPosition);
        }
        return validMoves;
    }
}

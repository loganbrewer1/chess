package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor teamTurn;

    public ChessGame() {
        //What else do I need here
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves;

        validMoves = board.getPiece(startPosition).pieceMoves(board,startPosition);

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPiece startPiece = board.getPiece(move.getStartPosition());

        //If move.getEndPosition is in check throw invalid move

        //If move.getEndPosition

        //Code for preventing king from moving into check

        //Code for moving a piece that would put the king in danger

        //Code for moving a piece or the king so that you are no longer in check

        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), startPiece);
        } else {
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece()));
        }


        // Call the isInCheck and isInCheckmate and isInSTal

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     *
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);

        return stillInCheck(kingPosition);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        boolean isInCheckmate = true;

        if (isInCheck(teamColor)) {
            for (ChessMove move : validMoves(kingPosition)) {
                if (!stillInCheck(move.getEndPosition())) {
                    isInCheckmate = false;
                    break;
                }
            }
        }

        return isInCheckmate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean isInStalemate = true;


        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(i,j));
                if (currentPiece.getTeamColor() == teamColor) {
                    if (validMoves(new ChessPosition(i,j)) != null) {
                        isInStalemate = false;
                        break;
                    }
                }
            }
        }

        return isInStalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    private ChessPosition getKingPosition(TeamColor teamColor) {
        ChessPosition kingPosition = null;

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(i, j));
                if (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(i,j);
                }
            }
        }
        return kingPosition;
    }

    private boolean stillInCheck(ChessPosition kingPosition) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                for (ChessMove move : validMoves(new ChessPosition(i,j))) {
                    if (move.getEndPosition() == kingPosition) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

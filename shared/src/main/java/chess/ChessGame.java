package chess;

import java.util.Collection;
import java.util.Collections;
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
        Collection<ChessMove> validMoves = Collections.emptyList();

        ChessPiece piece = board.getPiece(startPosition);
        if (piece != null) {
            validMoves = piece.pieceMoves(board, startPosition);
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessBoard oldBoard = board;
        ChessPiece startPiece = board.getPiece(move.getStartPosition());
        TeamColor teamColor = getTeamTurn();

        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), startPiece);
        } else {
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), new ChessPiece(teamColor, move.getPromotionPiece()));
        }

        if (isInCheckmate(teamColor)) {
            System.out.println("You lose");
            System.exit(0);
        }
        else if (isInStalemate(teamColor)) {
            System.out.println("Stalemate");
            System.exit(0);
        }
        else if (isInCheck(teamColor)) {
            setBoard(oldBoard);
            throw new InvalidMoveException("Invalid move");
        }
        else {
            if (teamColor == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            }
            else {
                setTeamTurn(TeamColor.WHITE);
            }
        }
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

        if (!isInCheck(teamColor)) {
            return false;
        }
        else if (isInCheck(teamColor)) {
            for (ChessMove move : validMoves(kingPosition)) {
                if (!stillInCheck(move.getEndPosition())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(i,j));
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    if (!validMoves(new ChessPosition(i,j)).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
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

        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(i, j));
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(i,j);
                }
            }
        }
        return kingPosition;
    }

    private boolean stillInCheck(ChessPosition kingPosition) {

        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
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

package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator {

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            //If there isn't a piece 1 position in front of it... it can move there
            if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                if (row != 7) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
                    //If it's still in it's starting row it can move two assuming there isn't a piece there
                    if (row == 2) {
                        if (board.getPiece(new ChessPosition(row + 2, col)) == null){
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
                        }
                    }
                }
                if (row == 7) {
                    addAllPromotions(validMoves, myPosition,row + 1, col);
                }
            }
            //If there are enemy pieces on a diagonal, those become valid moves.
            if (col + 1 < 8) {
                if (board.getPiece(new ChessPosition( row + 1, col + 1)) != null) {
                    if (board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        if (row != 7) {
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
                        }
                        if (row == 7) {
                            addAllPromotions(validMoves, myPosition, row + 1, col + 1);
                        }
                    }
                }
            }

            if (col - 1 > 0) {
                if (board.getPiece(new ChessPosition( row + 1, col - 1)) != null) {
                    if (board.getPiece(new ChessPosition( row + 1, col - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        if (row != 7) {
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));
                        }
                        if (row == 7) {
                            addAllPromotions(validMoves, myPosition, row + 1, col - 1);
                        }
                    }
                }
            }
        }


        if (myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            //If there isn't a piece 1 position in front of it... it can move there
            if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                if (row != 2) {
                    validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), null));
                    //If it's still in it's starting row it can move two assuming there isn't a piece there
                    if (row == 7) {
                        if (board.getPiece(new ChessPosition(row - 2, col)) == null){
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col), null));
                        }
                    }
                }
                if (row == 2) {
                    addAllPromotions(validMoves, myPosition,row - 1, col);
                }
            }
            //If there are enemy pieces on a diagonal, those become valid moves.
            if (col - 1 > 1) {
                if (board.getPiece(new ChessPosition( row - 1, col - 1)) != null) {
                    if (board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        if (row != 2) {
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));
                        }
                        if (row == 2) {
                            addAllPromotions(validMoves, myPosition, row - 1, col - 1);
                        }
                    }
                }
            }

            if (col + 1 < 8) {
                if (board.getPiece(new ChessPosition( row - 1, col + 1)) != null) {
                    if (board.getPiece(new ChessPosition( row - 1, col + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        if (row != 2) {
                            validMoves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));
                        }
                        if (row == 2) {
                            addAllPromotions(validMoves, myPosition, row - 1, col + 1);
                        }
                    }
                }
            }

        }

        return validMoves;
    }

    public void addAllPromotions(Collection<ChessMove> validMoves, ChessPosition myPosition, int row, int col) {
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), ChessPiece.PieceType.KNIGHT));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), ChessPiece.PieceType.BISHOP));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), ChessPiece.PieceType.ROOK));
        validMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), ChessPiece.PieceType.QUEEN));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

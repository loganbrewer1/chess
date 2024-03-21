package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class BoardUI {
    public void printBoard(ChessBoard board) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece piece = board.getPiece(new ChessPosition(i,j));
                if (piece != null) {
                    String pieceSymbol = String.valueOf(' ');
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_KING; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_KING;}
                    if (piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_QUEEN; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_QUEEN;}
                    if (piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_BISHOP; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_BISHOP;}
                    if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_KNIGHT; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_KNIGHT;}
                    if (piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_ROOK; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.ROOK && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_ROOK;}
                    if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.WHITE) {pieceSymbol = WHITE_PAWN; }
                    else if (piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == ChessGame.TeamColor.BLACK) {pieceSymbol = BLACK_PAWN;}
                    System.out.print("|" + pieceSymbol);
                } else {
                    System.out.print("| ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }

}

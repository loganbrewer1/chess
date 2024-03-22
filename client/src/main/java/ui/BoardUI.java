package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class BoardUI {
    public static void PrintBoardBlack(ChessBoard board) {
        System.out.print(SET_BG_COLOR_BLACK);
        String[] letters = new String[]{"","H","G","F","E","D","C","B","A", ""};
        String[] numbers = new String[]{"8","7","6","5","4","3","2","1"};
        for (String letter : letters) {
            System.out.print(letter + "   ");
        }
        System.out.println();
        for (int i = 7; i >= 0; i--) {
            System.out.print(numbers[i] + " ");
            for (int j = 7; j >= 0; j--) {
                if ((i + j) % 2 == 0 ) {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                } else  {
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                }
                PrintPieceType(board, i, j);
            }
            System.out.print(SET_BG_COLOR_BLACK);
            System.out.print(EMPTY + numbers[i]);
            System.out.println();
        }
        for (String letter : letters) {
            System.out.print(letter + "   ");
        }
    }

    public static void PrintBoardWhite(ChessBoard board) {
        System.out.print(SET_BG_COLOR_BLACK);
        String[] letters = new String[]{"A","B","C","D","E","F","G","H"};
        String[] numbers = new String[]{"8","7","6","5","4","3","2","1"};
        for (String letter : letters) {
            System.out.print(letter + "   ");
        }
        System.out.println();
        for (int i = 0; i <= 7; i++) {
            System.out.print(numbers[i] + " ");
            for (int j = 0; j <= 7; j++) {
                if ((i + j) % 2 == 0 ) {
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                } else  {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                }
                PrintPieceType(board, i, j);
            }
            System.out.print(SET_BG_COLOR_BLACK);
            System.out.print(EMPTY + numbers[i]);
            System.out.println();
        }
        for (String letter : letters) {
            System.out.print(letter + "   ");
        }
        System.out.println();
    }

    private static void PrintPieceType(ChessBoard board, int i, int j) {
        ChessPiece piece = board.getPiece(new ChessPosition(i + 1,j + 1));
        if (piece != null) {
            String pieceSymbol = EMPTY;
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
            System.out.print(pieceSymbol);
        } else {
            System.out.print(EMPTY);
        }
    }
}

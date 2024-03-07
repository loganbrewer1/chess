import chess.*;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import server.Server;

import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess server.Server: " + piece);
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
//        Server myServer = new Server();
//        myServer.run(8080);
    }
}
package service;

import chess.ChessBoard;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.Map;

public class GameService {

    private final AuthDAO authDatabase;
    private final GameDAO gameDatabase;
    private int nextGameID = 100;

    public GameService(AuthDAO authDatabase, GameDAO gameDatabase) {
        this.authDatabase = authDatabase;
        this.gameDatabase = gameDatabase;
    }

    public Map<Integer, GameData> ListGames(String authToken) {
        try {
            if (authDatabase.getAuth(authToken) == null) {
                throw new RuntimeException("Not a valid authToken");
            }
            return gameDatabase.listGames();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer CreateGame(String authToken, String gameName) {
        try {
            if (authDatabase.getAuth(authToken) == null) {
                throw new RuntimeException("Not a valid authToken");
            }
            int gameID = createGameID();
            gameDatabase.createGame(new GameData(gameID, null, null, gameName,new ChessGame()));
            return gameID;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void JoinGame() {}

    private int createGameID() {
        return nextGameID++;
    }
}

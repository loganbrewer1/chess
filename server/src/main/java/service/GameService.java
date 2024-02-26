package service;

import chess.ChessBoard;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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
    public void JoinGame(String authToken, String playerColor, int gameID) {
        //TODO: Not sure where I code for an observer
        try {
            String username = authDatabase.getAuth(authToken);
            GameData oldGame = gameDatabase.getGame(gameID);

            if (username == null) {
                throw new RuntimeException("Not a valid authToken");
            }

            if (Objects.equals(playerColor, "WHITE")) {
                gameDatabase.updateGame(new GameData(gameID, username, null, oldGame.gameName(),oldGame.game()));
            } else if (Objects.equals(playerColor, "BLACK")) {
                gameDatabase.updateGame(new GameData(gameID, null, username, oldGame.gameName(),oldGame.game()));
            }
            //Observer case? 
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int createGameID() {
        return nextGameID++;
    }
}
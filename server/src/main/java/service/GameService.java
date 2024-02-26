package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.Map;

public class GameService {

    private final AuthDAO authDatabase;
    private final GameDAO gameDatabase;

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
    public void CreateGame() {}
    public void JoinGame() {}
}

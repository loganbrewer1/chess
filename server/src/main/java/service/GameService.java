package service;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
import model.ListGamesResult;

import java.util.*;

public class GameService {

    private final AuthDAO authDatabase;
    private final GameDAO gameDatabase;
    private int nextGameID = 100;

    public GameService(AuthDAO authDatabase, GameDAO gameDatabase) {
        this.authDatabase = authDatabase;
        this.gameDatabase = gameDatabase;
    }

    public Map<String, List<ListGamesResult>> ListGames(String authToken) {
        try {
            if (authDatabase.getAuth(authToken) == null) {
                throw new RuntimeException("Not a valid authToken");
            }
            Map<Integer, GameData> allGames = gameDatabase.listGames();
            List<ListGamesResult> resultList = new ArrayList<>();
            for (GameData gameData : allGames.values()) {
                resultList.add(new ListGamesResult(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName()));
            }
            Map<String, List<ListGamesResult>> result = new HashMap<>();
            result.put("games", resultList);
            return result;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer CreateGame(String authToken, String gameName) { //After my change this probably won't work for my memoryDAO.
        try {
            if (authDatabase.getAuth(authToken) == null) {
                throw new RuntimeException("Not a valid authToken");
            }
            return gameDatabase.createGame(new GameData(createGameID(), null, null, gameName,new ChessGame()));
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
            if (oldGame == null) {
                throw new RuntimeException("Bad request");
            }
            if (Objects.equals(playerColor, "WHITE")) {
                if (oldGame.whiteUsername() != null) {
                    throw new RuntimeException("Already taken");
                }
                gameDatabase.updateGame(new GameData(gameID, username, oldGame.blackUsername(), oldGame.gameName(),oldGame.game()));
            } else if (Objects.equals(playerColor, "BLACK")) {
                if (oldGame.blackUsername() != null) {
                    throw new RuntimeException("Already taken");
                }
                gameDatabase.updateGame(new GameData(gameID, oldGame.whiteUsername(), username, oldGame.gameName(),oldGame.game()));
            }
            //Observer case?
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int createGameID() {
        return nextGameID++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameService that = (GameService) o;
        return nextGameID == that.nextGameID && Objects.equals(authDatabase, that.authDatabase) && Objects.equals(gameDatabase, that.gameDatabase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authDatabase, gameDatabase, nextGameID);
    }
}

package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO {

    private int nextGameID = 100;
    private final Map<Integer, GameData> gameMap = new HashMap<>();

    public void clearGames() {
        gameMap.clear();
    };

    public Integer createGame(GameData newGame) {
        int newID = createGameID();
        gameMap.put(newID, newGame);
        return newID;
    }

    public GameData getGame(int gameID) {
        return gameMap.get(gameID);
    }

    public Map<Integer, GameData> listGames() {
        return new HashMap<>(gameMap);
    }

    public void updateGame(GameData updatedGame) {
        gameMap.put(updatedGame.gameID(), updatedGame);
    }

    private int createGameID() {
        return nextGameID++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryGameDAO that = (MemoryGameDAO) o;
        return Objects.equals(gameMap, that.gameMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameMap);
    }
}

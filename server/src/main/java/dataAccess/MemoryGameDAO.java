package dataAccess;

import chess.ChessGame;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO {
    private final Map<Integer, ChessGame> gameMap = new HashMap<>();

    public void createGame(int gameID, ChessGame game) {
        gameMap.put(gameID, game);
    }

    public ChessGame getGame(int gameID) {
        return gameMap.get(gameID);
    }

    public Map<Integer, ChessGame> listGames() {
        return new HashMap<>(gameMap);
    }

    public void updateGame(int gameID, ChessGame updatedGame) {
        gameMap.put(gameID, updatedGame);
    }
}

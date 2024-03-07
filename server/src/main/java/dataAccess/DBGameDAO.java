package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBGameDAO implements GameDAO {
    private final Map<Integer, GameData> gameMap = new HashMap<>();

    public void clearGames() {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("DELETE FROM gamedata" )) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public void createGame(GameData newGame) {
        gameMap.put(newGame.gameID(), newGame);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBGameDAO that = (DBGameDAO) o;
        return Objects.equals(gameMap, that.gameMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameMap);
    }
}

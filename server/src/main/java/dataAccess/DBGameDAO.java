package dataAccess;

import com.google.gson.Gson;
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

    public Integer createGame(GameData newGame) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO gamedata (whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?, ?)" )) {
                preparedStatement.setString(1,null);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, newGame.gameName());

                var gameJson = new Gson().toJson(newGame.game());
                preparedStatement.setString(4, gameJson);

                var resultSet = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }

                preparedStatement.executeUpdate();
                return ID;
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
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

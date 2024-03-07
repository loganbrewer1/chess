package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

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

                var rs = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (rs.next()) {
                    ID = rs.getInt(1);
                }

                preparedStatement.executeUpdate();
                return ID;
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGame(int gameID) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM gamedata WHERE gameID = ?" )) {
                preparedStatement.setInt(1, gameID);
                var rs = preparedStatement.executeQuery();
                String gameJson = rs.getString("game"); //TODO: Might need to check rs.next
                ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
                return new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), game);
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, GameData> listGames() {
        Map<Integer, GameData> allGames = new HashMap<>();

        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM gamedata" )) {
                var rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String gameJson = rs.getString("game");
                    ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
                    allGames.put(rs.getInt("gameID"),new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), game));
                }
                return allGames;
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame(GameData updatedGame) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("UPDATE gamedata SET whiteusername = ?, blackusername = ?, gameName = ?, and game = ? WHERE gameID = ?" )) {
                preparedStatement.setString(1, updatedGame.whiteUsername());
                preparedStatement.setString(2, updatedGame.blackUsername());
                preparedStatement.setString(3, updatedGame.gameName());
                preparedStatement.setString(4, new Gson().toJson(updatedGame.game()));
                preparedStatement.executeQuery();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
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

package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBGameDAO implements GameDAO {

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
            try (var preparedStatement = conn.prepareStatement("INSERT INTO gamedata (whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS )) {
                preparedStatement.setString(1, newGame.whiteUsername());
                preparedStatement.setString(2, newGame.blackUsername());
                preparedStatement.setString(3, newGame.gameName());

                var gameJson = new Gson().toJson(newGame.game());
                preparedStatement.setString(4, gameJson);
                preparedStatement.executeUpdate();

                var rs = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (rs.next()) {
                    ID = rs.getInt(1);
                }

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
                if (rs.next()) {
                    String gameJson = rs.getString("game"); //TODO: Might need to check rs.next
                    ChessGame game = new Gson().fromJson(gameJson, ChessGame.class);
                    return new GameData(rs.getInt("gameID"), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), game);
                }
                else {
                    return null;
                }
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
            try (var preparedStatement = conn.prepareStatement("UPDATE gamedata SET whiteusername = ?, blackusername = ?, gameName = ?, game = ? WHERE gameID = ?" )) {
                preparedStatement.setString(1, updatedGame.whiteUsername());
                preparedStatement.setString(2, updatedGame.blackUsername());
                preparedStatement.setString(3, updatedGame.gameName());
                preparedStatement.setString(4, new Gson().toJson(updatedGame.game()));
                preparedStatement.setInt(5, updatedGame.gameID());
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

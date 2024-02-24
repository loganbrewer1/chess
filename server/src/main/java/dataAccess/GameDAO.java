package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Map;

public interface GameDAO {
    void clearGames() throws DataAccessException;
    void createGame(GameData newGame) throws DataAccessException;
    GameData getGame(int gameId) throws DataAccessException;
    void updateGame(GameData updatedGame);
    Map<Integer, GameData> listGames();
}
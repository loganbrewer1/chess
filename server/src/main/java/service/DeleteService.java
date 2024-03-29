package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class DeleteService {
    private final UserDAO userDatabase;
    private final AuthDAO authDatabase;
    private final GameDAO gameDatabase;

    public DeleteService(UserDAO userDatabase, AuthDAO authDatabase, GameDAO gameDatabase) {
        this.userDatabase = userDatabase;
        this.authDatabase = authDatabase;
        this.gameDatabase = gameDatabase;
    }

    public void DeleteEverything() {
        try {
            userDatabase.clearUsers();
            authDatabase.clearAuth();
            gameDatabase.clearGames();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

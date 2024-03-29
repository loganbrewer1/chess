package dataAccess;

import model.AuthData;

public interface AuthDAO {
    void clearAuth() throws DataAccessException;
    void insertAuth(AuthData authData) throws DataAccessException;
    String getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
}

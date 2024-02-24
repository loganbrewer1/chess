package dataAccess;

import model.UserData;

public interface UserDAO {
    void clearUsers() throws DataAccessException;
    void insertUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
}

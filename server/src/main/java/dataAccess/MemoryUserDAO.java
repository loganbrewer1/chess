package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO {
    private final Map<String, UserData> users = new HashMap<>();

   public void clear() throws DataAccessException {
        users.clear();
    }

    public void insertUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
    }

    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }
}

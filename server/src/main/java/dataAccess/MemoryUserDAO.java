package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO {
    private final Map<String, UserData> users = new HashMap<>();

   public void clearUsers() throws DataAccessException {
        users.clear();
    }

    public void insertUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
    }

    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryUserDAO that = (MemoryUserDAO) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }
}

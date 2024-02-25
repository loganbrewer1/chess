package dataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryAuthDAO implements AuthDAO {
    private final Map<String, String> authTokenMap = new HashMap<>();

    public void createAuth(AuthData authData) {
        authTokenMap.put(authData.authToken(), authData.username());
    }

    public String getAuth(String authToken) {
        return authTokenMap.get(authToken);
    }

    public void deleteAuth(String authToken) {
        authTokenMap.remove(authToken);
    }

    public void clearAuth() {
        authTokenMap.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryAuthDAO that = (MemoryAuthDAO) o;
        return Objects.equals(authTokenMap, that.authTokenMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authTokenMap);
    }
}


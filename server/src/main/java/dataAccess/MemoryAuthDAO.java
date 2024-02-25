package dataAccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    private final Map<String, String> authTokenMap = new HashMap<>();

    public void createAuth(AuthData authData) {
        authTokenMap.put(authData.username(), authData.authToken());
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
}


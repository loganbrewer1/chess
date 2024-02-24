package dataAccess;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO {
    private final Map<String, String> authTokenMap = new HashMap<>(); // Map to store authToken-user mappings

    public void createAuth(String authToken, String username) {
        authTokenMap.put(authToken, username);
    }

    public String getAuth(String authToken) {
        return authTokenMap.get(authToken);
    }

    public void deleteAuth(String authToken) {
        authTokenMap.remove(authToken);
    }
}


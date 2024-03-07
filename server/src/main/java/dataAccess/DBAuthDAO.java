package dataAccess;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBAuthDAO implements AuthDAO {
    private final Map<String, String> authTokenMap = new HashMap<>();

    public void insertAuth(AuthData authData) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO AuthData (authToken, username) VALUES(?, ?)" )) {
                preparedStatement.setString(1, authData.authToken());
                preparedStatement.setString(2, authData.username());

                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAuth(String authToken) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM AuthData WHERE authToken = ?" )) {
                preparedStatement.setString(1, authToken);
                var rs = preparedStatement.executeQuery();
                return rs.getString("authToken");
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
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
        DBAuthDAO that = (DBAuthDAO) o;
        return Objects.equals(authTokenMap, that.authTokenMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authTokenMap);
    }
}


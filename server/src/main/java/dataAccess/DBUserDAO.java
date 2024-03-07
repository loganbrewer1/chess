package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBUserDAO implements UserDAO {
    private final Map<String, UserData> users = new HashMap<>();

   public void clearUsers() throws DataAccessException {
        users.clear();
   }

    public void insertUser(UserData user) throws DataAccessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());

        var conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO UserData (username, password, email) VALUES(?, ?, ?)" )) {
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, user.email());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        var conn = DatabaseManager.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT * FROM UserData WHERE username = ?" )) {
            preparedStatement.setString(1, username);
            var rs = preparedStatement.executeQuery();
            return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBUserDAO that = (DBUserDAO) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }
}

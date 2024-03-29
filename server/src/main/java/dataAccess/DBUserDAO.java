package dataAccess;

import model.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBUserDAO implements UserDAO {

   public void clearUsers() {
       try {
           var conn = DatabaseManager.getConnection();
           try (var preparedStatement = conn.prepareStatement("DELETE FROM userdata" )) {
               preparedStatement.executeUpdate();
           }
       } catch (DataAccessException | SQLException e) {
           throw new RuntimeException(e);
       }
   }

    public void insertUser(UserData user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());

        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO userdata (username, password, email) VALUES(?, ?, ?)" )) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, user.email());

                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserData getUser(String username) {
        try {
            var conn = DatabaseManager.getConnection();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM userdata WHERE username = ?" )) {
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                }
                else {
                    return null;
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

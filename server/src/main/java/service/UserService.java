package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

import java.util.Objects;

public class UserService {
    private final UserDAO userDatabase;
    private final AuthDAO authDatabase;
    private int authTokenCounter = 1;

    public UserService(UserDAO userDatabase, AuthDAO authDatabase) {
        this.userDatabase = userDatabase;
        this.authDatabase = authDatabase;
    }
    public AuthData register(UserData user) {
        try {
            if (userDatabase.getUser(user.username()) == null) {
                userDatabase.insertUser(user);
            }  else  {
                throw new RuntimeException("Username already exists");
            }
            String authToken = CreateAuthToken();

            return new AuthData(user.username(), authToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResult login(LoginRequest login) {
        try {
            if (userDatabase.getUser(login.username()) == null) {
                throw new RuntimeException("Could not find username");
            }
            if (!Objects.equals(userDatabase.getUser(login.username()).password(), login.password())) { //Intelleji suggested this instead of !=. Check here later if something does not work.
                throw new RuntimeException("Password does not match");
            }
            String newAuthToken = CreateAuthToken();
            authDatabase.createAuth(new AuthData(newAuthToken, login.username()));
            return new LoginResult(login.username(), newAuthToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void logout(String authToken) {
        try {
            if (authDatabase.getAuth(authToken) == null) {
                throw new RuntimeException("Not a valid authToken");
            }
            authDatabase.deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String CreateAuthToken() {
        return "myCoolAuthToken" + authTokenCounter++;
    }
}
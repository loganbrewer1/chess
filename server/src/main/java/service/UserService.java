package service;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    private final UserDAO userDatabase;
    private int authTokenCounter = 1;

    public UserService(UserDAO userDatabase) {
        this.userDatabase = userDatabase;
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

    public void login( UserData user) {}
    public void logout(UserData user) {}

    private String CreateAuthToken() {
        return "myCoolAuthToken" + authTokenCounter++;
    }
}
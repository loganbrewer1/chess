package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.UserData;
import service.DeleteService;

import static org.junit.jupiter.api.Assertions.*;

class DBUserDAOTest {

    @BeforeEach
    void setUp() {
        new DeleteService(new DBUserDAO(), new DBAuthDAO(), new DBGameDAO()).DeleteEverything();
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void clearUsers() {
        UserData myUser = new UserData( "lbrewer4", "yowasup", "lbrewer4@byu.edu");
        new DBUserDAO().insertUser(myUser);
        new DBUserDAO().clearUsers();
        UserData person = new DBUserDAO().getUser("lbrewer4");
        assertEquals(null, person, "Empty database should return person as null");
    }

    @Test
    void insertUserPositive() {
        UserData myUser = new UserData( "lbrewer4", "yowasup", "lbrewer4@byu.edu");
        new DBUserDAO().insertUser(myUser);
        assertEquals(myUser.email(), new DBUserDAO().getUser("lbrewer4").email(), "Should be equal");
    }

    @Test
    void insertUserNegative() {
        String test = "Improperly succeeded";
        UserData myUser = new UserData( null, "kronk123", "kronk@kronk.net");
        try {
            new DBUserDAO().insertUser(myUser);
        } catch (RuntimeException e) {
            test = "Properly failed";
        }
        assertEquals("Properly failed", test, "Null username inserted");
    }

    @Test
    void getUserPositive() {
        UserData myUser = new UserData( "lbrewer4", "yowasup", "lbrewer4@byu.edu");
        new DBUserDAO().insertUser(myUser);
        new DBUserDAO().getUser("lbrewer4");
        assertEquals("lbrewer4@byu.edu", new DBUserDAO().getUser("lbrewer4").email(), "Should be equal");
    }

    @Test
    void getUserNegative() {
        UserData person = new DBUserDAO().getUser(null);
        assertEquals(null, person, "Null get should return null");
    }
}
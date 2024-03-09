package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DeleteService;

import static org.junit.jupiter.api.Assertions.*;

class DBAuthDAOTest {

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
    void insertAuthPositive() {
        AuthData myAuth = new AuthData( "myCoolAuth", "lbrewer4");
        new DBAuthDAO().insertAuth(myAuth);
        assertEquals(myAuth.username(), new DBAuthDAO().getAuth("myCoolAuth"), "Should be equal");
    }

    @Test
    void insertAuthNegative() {
        String test = "Improperly succeeded";
        try {
            new DBAuthDAO().insertAuth(new AuthData( null, "lbrewer4"));
        } catch (RuntimeException e) {
            test = "Properly failed";
        }
        assertEquals("Properly failed", test, "Null username inserted");
    }

    @Test
    void getAuthPositive() {
        new DBAuthDAO().insertAuth(new AuthData( "myCoolAuth", "lbrewer4"));
        assertEquals("lbrewer4", new DBAuthDAO().getAuth("myCoolAuth"), "Should be equal");
    }

    @Test
    void getAuthNegative() {
        String myAuth = new DBAuthDAO().getAuth("");
        assertEquals(null, myAuth, "myAuth should be null");
    }

    @Test
    void deleteAuthPositive() {
        new DBAuthDAO().insertAuth(new AuthData( "myCoolAuth", "lbrewer4"));
        new DBAuthDAO().deleteAuth("myCoolAuth");
        assertNull(new DBAuthDAO().getAuth("myCoolAuth"));
    }

//    @Test
//    void deleteAuthNegative() {
//    }

    @Test
    void clearAuth() {
        new DBAuthDAO().insertAuth(new AuthData( "myCoolAuth", "lbrewer4"));
        new DBAuthDAO().clearAuth();
        assertNull(new DBAuthDAO().getAuth("myCoolAuth"));
    }
}
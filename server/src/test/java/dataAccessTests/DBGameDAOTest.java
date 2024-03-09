package dataAccessTests;

import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DeleteService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DBGameDAOTest {

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
    void clearGames() {
        GameData myGame = new GameData( 101, null, null, "KronkIsBabe", null);
        new DBGameDAO().createGame(myGame);
        new DBGameDAO().clearGames();
        GameData deletedGame = new DBGameDAO().getGame(101);
        assertEquals(null, deletedGame, "Empty database should return person as null");
    }

    @Test
    void createGamePositive() {
        GameData myGame = new GameData( 101, null, null, "TurkeyBacon", null);
        String test = null;
        try {
            new DBGameDAO().createGame(myGame);
        } catch (RuntimeException e) {
            test = "test failed";
        }
        assertNull(test, "Runtime error occurred");
    }

    @Test
    void createGameNegative() {
        String test = "Improperly succeeded";
        GameData myGame = new GameData( 101, null, null, null, null);
        try {
            new DBGameDAO().createGame(myGame);
        } catch (RuntimeException e) {
            test = "Properly failed";
        }
        assertEquals("Properly failed", test, "Game name is null");
    }

    @Test
    void getGamePositive() {
        GameData myGame = new GameData( 101, null, null, "TurkeyBacon", null);
        String test = null;
        new DBGameDAO().createGame(myGame);
        try {
            new DBGameDAO().getGame(101);
        } catch (RuntimeException e) {
            test = "test failed";
        }
        assertNull(test, "Runtime error occurred");
    }

    @Test
    void getGameNegative() {
        GameData resultGame = new DBGameDAO().getGame(12321);
        assertNull(resultGame, "Accessed game that doesn't exist");
    }

    @Test
    void listGamesPositive() {
        GameData myGame = new GameData( 101, null, null, "TurkeyBacon", null);
        GameData anotherGame = new GameData( 101, null, null, "TurkeyBacon", null);
        String test = null;
        new DBGameDAO().createGame(myGame);
        new DBGameDAO().createGame(anotherGame);
        try {
            new DBGameDAO().listGames();
        } catch (RuntimeException e) {
            test = "test failed";
        }
        assertNull(test, "Runtime error occurred");
    }

    @Test
    void listGamesNegative() {
        GameData myGame = new GameData( 101, null, null, "TurkeyBacon", null);
        GameData anotherGame = new GameData( 101, null, null, "TurkeyBacon", null);
        new DBGameDAO().createGame(myGame);
        new DBGameDAO().createGame(anotherGame);
        Map<Integer, GameData> outputGames = new DBGameDAO().listGames();
        assertNotEquals("[]", outputGames, "Didn't output any games");
    }

    @Test
    void updateGamePositive() {
        String test = null;
        GameData myGame = new GameData( 101, null, null, "TurkeyBacon", null);
        int myGameID = new DBGameDAO().createGame(myGame);
        GameData updatedGame = new GameData( myGameID, "lbrewer4", null, "TurkeyBacon", null);
        try {
            new DBGameDAO().updateGame(updatedGame);
        } catch (RuntimeException e) {
            test = "test failed";
        }
        assertNull(test, "Runtime error occurred");
    }

    @Test
    void updateGameNegative() {
        GameData updatedGame = new GameData( 153243, "lbrewer4", null, "TurkeyBacon", null);
        new DBGameDAO().updateGame(updatedGame);
        GameData resultGame = new DBGameDAO().getGame(153243);
        assertNull(resultGame, "Updated game created new game");
    }
}
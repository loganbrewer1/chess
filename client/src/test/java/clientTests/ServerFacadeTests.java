package clientTests;

import dataAccess.*;
import org.junit.jupiter.api.*;
import server.Server;
import service.DeleteService;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static Server server;

    @BeforeEach
    void setUp() {
        new DeleteService(new DBUserDAO(), new DBAuthDAO(), new DBGameDAO()).DeleteEverything();
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void registerPositive() {
        String[] args = new String[] {"Register", "Michael", "password", "email"};
        try {
            ServerFacade.Register(args);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void registerNegative() {
        String[] args = new String[] {"Register", "Michael", "password"};
        String auth = null;
        try {
            auth = ServerFacade.Register(args);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
        Assertions.assertNull(auth);
    }

    @Test
    public void loginPositive() {
        String[] args = new String[] {"login", "Michael", "password"};
        String auth = null;
        try {
            ServerFacade.Register(new String[] {"register", "Michael", "password", "email"});
            auth = ServerFacade.Login(args);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
        Assertions.assertNotNull(auth);
    }

    @Test
    public void loginNegative() {
        String[] args = new String[] {"login", "Michael", "password"};
        String auth = null;
        try {
            auth = ServerFacade.Login(args);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
        Assertions.assertNull(auth);
    }
    @Test
    public void createGamePositive() {
        String[] args = new String[] {"create", "Magnus"};
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            ServerFacade.CreateGame(args, auth);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void createGameNegative() {
        String[] args = new String[] {"create"};
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            Assertions.assertThrows(Exception.class, () -> ServerFacade.CreateGame(args, auth));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void listGamesPositive() {
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            ServerFacade.CreateGame(new String[] {"create", "Magnus"}, auth);
            Assertions.assertDoesNotThrow(() -> ServerFacade.ListGames(auth));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void listGamesNegative() {
        try {
            Assertions.assertThrows(Exception.class, () -> ServerFacade.ListGames("badAuthToken"));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void joinGamePositive() {
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            String gameID = ServerFacade.CreateGame(new String[] {"create", "Magnus"}, auth);
            Assertions.assertDoesNotThrow(() -> ServerFacade.JoinGame(new String[] {"join", gameID, "WHITE"}, auth));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void joinGameNegative() {
        String[] args = new String[] {"join", "badGameID", "WHITE"};
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            ServerFacade.CreateGame(new String[] {"create", "Magnus"}, auth);
            Assertions.assertThrows(Exception.class, () -> ServerFacade.JoinGame(args, auth));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void logoutPositive() {
        try {
            String auth = ServerFacade.Register(new String[] {"register", "Magnus", "password", "email"});
            Assertions.assertDoesNotThrow(() -> ServerFacade.Logout(auth));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void logoutNegative() {
        try {
            Assertions.assertThrows(Exception.class, () -> ServerFacade.Logout("badAuthToken"));
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

}

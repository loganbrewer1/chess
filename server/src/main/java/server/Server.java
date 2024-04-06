package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.*;
import service.DeleteService;
import service.GameService;
import service.UserService;
import spark.*;

import java.sql.SQLException;
import java.util.Map;

public class Server {
    private final UserService userService;
    private final GameService gameService;
    private final DeleteService deleteService;

    public Server() {
        AuthDAO authDatabase = new DBAuthDAO();
        GameDAO gameDatabase = new DBGameDAO();
        UserDAO userDatabase = new DBUserDAO();

        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        this.userService = new UserService(userDatabase, authDatabase);
        this.gameService = new GameService(authDatabase, gameDatabase);
        this.deleteService = new DeleteService(userDatabase,authDatabase,gameDatabase);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        // Register your endpoints and handle exceptions here.

        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);
        Spark.delete("/session", this::LogoutHandler);
        Spark.get("/game", this::ListGamesHandler);
        Spark.post("/singleGame", this::SingleGameHandler);
        Spark.post("/game", this::CreateGameHandler);
        Spark.put("/game", this::JoinGameHandler);
        Spark.delete("/db", this::DeleteHandler);
        Spark.exception(Exception.class, this::errorHandler);
        Spark.notFound((req, res) -> {
            var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
            return errorHandler(new Exception(msg), req, res);
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object SingleGameHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            res.type("application/json");
            Map gameIDMap = new Gson().fromJson(req.body(), Map.class);

            String checkGameID = gameIDMap.get("gameID").toString();
            int gameId = Integer.parseInt(checkGameID);
            return new Gson().toJson(gameService.getGame(authToken, gameId));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Not a valid authToken")) {
                res.status(401);
            }
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }

    }

    private Object RegisterHandler(Request req, Response res) {
        try {
            UserData bodyObj = getBody(req, UserData.class);
            AuthData authData = userService.register(bodyObj);
            res.type("application/json");
            return new Gson().toJson(authData);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Bad request")) {
                res.status(400);
                return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            } else if (e.getMessage().equals("Username already exists")) {
                res.status(403);
                return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            } else {
                res.status(500);
                return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
            }
        }
    }
    private Object JoinGameHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            JoinGameRequest bodyObj = getBody(req, JoinGameRequest.class);
            gameService.JoinGame(authToken, bodyObj.playerColor(), bodyObj.gameID());
            res.type("application/json");
            return new Gson().toJson(Map.of("message", "Join successful"));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Bad request")) {
                res.status(400);
            }
            else if (e.getMessage().equals("Not a valid authToken")) {
                res.status(401);
            }
            else if (e.getMessage().equals("Already taken")) {
                res.status(403);
            }
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object CreateGameHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            CreateGameRequest bodyObj = getBody(req, CreateGameRequest.class);
            int gameID = gameService.CreateGame(authToken, bodyObj.gameName());
            res.type("application/json");
            return new Gson().toJson(Map.of("gameID", gameID));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Not a valid authToken")) {
                res.status(401);
            }
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object ListGamesHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            res.type("application/json");
            return new Gson().toJson(gameService.ListGames(authToken));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Not a valid authToken")) {
                res.status(401);
            }
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object LoginHandler(Request req, Response res) {
        try {
            UserData bodyObj = getBody(req, UserData.class);
            return new Gson().toJson(userService.login(new LoginRequest(bodyObj.username(), bodyObj.password())));
        } catch (RuntimeException e) {
            res.status(401);
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object LogoutHandler(Request req, Response res) {
        try {
            String authToken = req.headers("Authorization");
            userService.logout(authToken);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(Map.of("message", "Logout successful"));
        }   catch (RuntimeException e) {
            if (e.getMessage().equals("Not a valid authToken")) {
                res.status(401);
            }
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object DeleteHandler(Request req, Response res) {
        try {
            deleteService.DeleteEverything();
            res.status(200);
            return new Gson().toJson(Map.of("message", "Data Deleted"));
        } catch (RuntimeException e) {
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    public Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

    private static <T> T getBody(Request request, Class<T> clazz) {
        T body = new Gson().fromJson(request.body(), clazz);
        if (body == null) {
            throw new RuntimeException("Missing required body");
        }
        return body;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

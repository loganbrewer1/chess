package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.*;
import model.AuthData;
import model.JoinGameRequest;
import model.LoginRequest;
import model.UserData;
import service.DeleteService;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.Map;
import java.util.Objects;

public class Server {
    private final UserService userService;
    private final GameService gameService;
    private final DeleteService deleteService;

    public Server() {
        AuthDAO authDatabase = new MemoryAuthDAO();
        GameDAO gameDatabase = new MemoryGameDAO();
        UserDAO userDatabase = new MemoryUserDAO();

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
        Spark.post("/game", this::CreateGameHandler);
        Spark.put("/game", this::JoinGameHandler);
        Spark.delete("/db", this::DeleteHandler);
        Spark.get("/error", this::throwError);

        Spark.exception(Exception.class, this::errorHandler);
        Spark.notFound((req, res) -> {
            var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
            return errorHandler(new Exception(msg), req, res);
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object RegisterHandler(Request req, Response res) { //How do I test this?
        UserData bodyObj = getBody(req, UserData.class);
        AuthData authData = userService.register(bodyObj);
        res.type("application/json");
        return new Gson().toJson(authData);
    }
    private Object JoinGameHandler(Request req, Response res) {
        String authToken = req.headers("Authorization");
        JoinGameRequest bodyObj = getBody(req, JoinGameRequest.class);
        gameService.JoinGame(authToken, bodyObj.playerColor(), bodyObj.gameID());
        res.type("application/json");
        return new Gson().toJson(Map.of("message", "Join successful"));
    }

    private Object CreateGameHandler(Request req, Response res) {
        String authToken = req.headers("Authorization");
        Map<String, String> bodyObj = getBody(req, Map.class);
        String gameName = bodyObj.get("gameName");
        int gameID = gameService.CreateGame(authToken, gameName);
        res.type("application/json");
        return new Gson().toJson(Map.of("gameID", gameID));
    }

    private Object ListGamesHandler(Request req, Response res) {
        String authToken = req.headers("Authorization");
        res.type("application/json");
        return new Gson().toJson(gameService.ListGames(authToken));
    }

    private Object LoginHandler(Request req, Response res) {
        try {
            UserData bodyObj = getBody(req, UserData.class);
            return new Gson().toJson(userService.login(new LoginRequest(bodyObj.username(), bodyObj.password())));
        } catch (RuntimeException e) {
            res.status(401); // Unauthorized
            return new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        }
    }

    private Object LogoutHandler(Request req, Response res) {
        String authToken = req.headers("Authorization");
        userService.logout(authToken);
        res.status(200);
        res.type("application/json");
        return new Gson().toJson(Map.of("message", "Logout successful"));
    }

    private Object DeleteHandler(Request req, Response res) {
        String authToken = req.headers("Authorization");
        deleteService.DeleteEverything(authToken);
        res.status(200);
        return new Gson().toJson(Map.of("message", "Data Deleted"));
    }

    private Object throwError(Request req, Response res) {
        throw new RuntimeException("Server on fire");
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

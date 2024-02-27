package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.AuthData;
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
/*        Spark.post("/session", this::LoginHandler);
        Spark.delete("/session", this::LogoutHandler);
        Spark.get("/game", this::ListGamesHandler);
        Spark.post("/game", this::CreateGameHandler);
        Spark.put("/game", this::JoinGameHandler);
        Spark.delete("/db", this::DeleteHandler);*/
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
        var serializer = new Gson();
        UserData newUser = serializer.fromJson(req.body(), UserData.class);
        AuthData authData = userService.register(newUser);
        res.type("application/json");
        return serializer.toJson(authData);
    }

    /*private Object JoinGameHandler(Request req, Response res) {
    }

    private Object CreateGameHandler(Request req, Response res) {
    }

    private Object ListGamesHandler(Request req, Response res) {
    }

    private Object LogoutHandler(Request req, Response res) {
    }

    private Object LoginHandler(Request req, Response res) {
    }

    private Object DeleteHandler(Request req, Response res) {
    }*/

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

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

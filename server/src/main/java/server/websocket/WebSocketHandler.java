package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/connect", WebSocketHandler.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> handleJoinPlayer(session, message);
            case JOIN_OBSERVER -> handleJoinObserver(session,message);
            case LEAVE -> handleLeave(session, message);
            case MAKE_MOVE -> handleMakeMove(session, message);
            case RESIGN -> handleResign(session, message);
        }
    }

    public void handleJoinPlayer(Session session, String message) {
        JoinPlayer command = new Gson().fromJson(message, JoinPlayer.class);
        String playerName = command.getVisitorName();
        connections.add(playerName, session);

        //Add logic for LOAD_GAME. What does the game state mean? 

        var messageToSend = String.format(playerName + " joined the game as the " + command.getPlayerColor() + " player.");
        var notification = new NotificationMessage(messageToSend);

        try {
            connections.broadcast(playerName, notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleJoinObserver(Session session, String message) {
        JoinObserver command = new Gson().fromJson(message, JoinObserver.class);
    }

    public void handleMakeMove(Session session, String message) {
        MakeMove command = new Gson().fromJson(message, MakeMove.class);
    }

    public void handleLeave(Session session, String message) {
        Leave command = new Gson().fromJson(message, Leave.class);
    }

    public void handleResign(Session session, String message) {
        Resign command = new Gson().fromJson(message, Resign.class);
    }
}
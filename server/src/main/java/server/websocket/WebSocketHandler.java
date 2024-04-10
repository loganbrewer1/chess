package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DBGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
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

        if (command == null) {
            try {
                session.getRemote().sendString(new ErrorMessage("WebSocket response: Error, command not valid.").getErrorMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String playerName = command.getVisitorName();
            connections.add(playerName, session);

            var messageToSend = String.format(playerName + " joined the game as the " + command.getPlayerColor() + " player.");
            var notification = new NotificationMessage(messageToSend);

            try {
                session.getRemote().sendString(new LoadGameMessage("LOAD GAME").toString());
                connections.broadcast(playerName, notification);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleJoinObserver(Session session, String message) {
        JoinObserver command = new Gson().fromJson(message, JoinObserver.class);
        if (command == null) {
            try {
                session.getRemote().sendString(new ErrorMessage("WebSocket response: Error, command not valid.").getErrorMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String playerName = command.getVisitorName();
            connections.add(playerName, session);

            var messageToSend = String.format(playerName + " joined the game as an observer.");
            var notification = new NotificationMessage(messageToSend);

            try {
                session.getRemote().sendString(new LoadGameMessage("LOAD GAME").toString());
                connections.broadcast(playerName, notification);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleMakeMove(Session session, String message) {
        MakeMove command = new Gson().fromJson(message, MakeMove.class);
        if (command == null) {
            try {
                session.getRemote().sendString(new ErrorMessage("WebSocket response: Error, command not valid.").getErrorMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            GameData gameData = new DBGameDAO().getGame(command.getGameID());
            ChessMove move = command.getMove();
            ChessGame.TeamColor turnColor = gameData.game().getTeamTurn();
            String playerName = command.getVisitorName();
            String opponentName = gameData.blackUsername();

            if (turnColor == ChessGame.TeamColor.BLACK) {
                opponentName = gameData.whiteUsername();
            }

            //Attempt make move, and throw error if invalid move
            try {
                gameData.game().makeMove(move);
                //Load game message
                try {
                    session.getRemote().sendString(new LoadGameMessage("LOAD GAME").toString());
                    connections.broadcast(playerName, new LoadGameMessage("LOAD GAME"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    var messageToSend = playerName + " made a the move " + convertedPosition(move.getStartPosition()) + " to " + convertedPosition(move.getEndPosition());
                    var notification = new NotificationMessage(messageToSend);
                    session.getRemote().sendString(notification.toString());
                    connections.broadcast(playerName, notification);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (InvalidMoveException e) {
                try {
                    session.getRemote().sendString(new ErrorMessage("WebSocket response: Error, not a valid move").getErrorMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            //Checks for checkmate, stalemate, and check. Sends appropriate messages.
            if (gameData.game().isInCheckmate(gameData.game().getTeamTurn())) {
                try {
                    var messageToSend = String.format(opponentName + " is in checkmate.");
                    var notification = new NotificationMessage(messageToSend);
                    session.getRemote().sendString(notification.toString());
                    connections.broadcast(playerName, notification);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (gameData.game().isInStalemate(gameData.game().getTeamTurn())) {
                try {
                    var messageToSend = "Stalemate. Game is over";
                    var notification = new NotificationMessage(messageToSend);
                    session.getRemote().sendString(notification.toString());
                    connections.broadcast(playerName, notification);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (gameData.game().isInCheck(gameData.game().getTeamTurn())) {
                try {
                    var messageToSend = String.format(opponentName + " is in check.");
                    var notification = new NotificationMessage(messageToSend);
                    session.getRemote().sendString(notification.toString());
                    connections.broadcast(playerName, notification);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String convertedPosition(ChessPosition position) {
        char file = 'z';
        switch (position.getColumn()) {
            case 0 -> file = 'a';
            case 1 -> file = 'b';
            case 2 -> file = 'c';
            case 3 -> file = 'd';
            case 4 -> file = 'e';
            case 5 -> file = 'f';
            case 6 -> file = 'g';
            case 7 -> file = 'h';
        }

        int rank = 8 - position.getRow();
        return "" + file + rank;
    }

    public void handleLeave(Session session, String message) {
        Leave command = new Gson().fromJson(message, Leave.class);
    }

    public void handleResign(Session session, String message) {
        Resign command = new Gson().fromJson(message, Resign.class);
    }
}
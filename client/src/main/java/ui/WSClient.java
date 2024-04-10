package ui;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient extends Endpoint {

    public static void main(String[] args) throws Exception {
        var ws = new WSClient();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo");
        while (true) {
            ws.send(scanner.nextLine());
        }
    }

    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                System.out.println(message);
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case ERROR -> HandleErrorMessage(session, message);
                    case LOAD_GAME -> HandleLoadGame(session,message);
                    case NOTIFICATION -> HandleNotification(session, message);
                }
            }
        });
    }

    private void HandleNotification(Session session, String message) {
        NotificationMessage notification = new Gson().fromJson(message, NotificationMessage.class);
        System.out.println(notification.getMessage());
    }

    private void HandleLoadGame(Session session, String message) {
        LoadGameMessage notification = new Gson().fromJson(message, LoadGameMessage.class);
        System.out.println(notification.getGame());
    }

    private void HandleErrorMessage(Session session, String message) {
        ErrorMessage notification = new Gson().fromJson(message, ErrorMessage.class);
        System.out.println(notification.getErrorMessage());
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
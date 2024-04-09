package webSocketMessages.userCommands;

import chess.ChessMove;

public class JoinObserver extends UserGameCommand {

    private final int gameID;
    private final String visitorName;

    public JoinObserver(String authToken, int gameID, String visitorName) {
        super(authToken);
        this.visitorName = visitorName;
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getVisitorName() {
        return visitorName;
    }
}

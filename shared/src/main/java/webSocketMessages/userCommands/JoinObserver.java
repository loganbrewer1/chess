package webSocketMessages.userCommands;

import chess.ChessMove;

public class JoinObserver extends UserGameCommand {

    private final int gameID;

    public JoinObserver(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}

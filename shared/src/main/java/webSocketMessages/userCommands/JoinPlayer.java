package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
    private final Integer gameID;
    private final ChessGame.TeamColor playerColor;
    private final String visitorName;
    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor, String visitorName) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.visitorName = visitorName;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public String getVisitorName() {
        return visitorName;
    }
}

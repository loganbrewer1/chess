package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {

    private final int gameID;
    private final String visitorName;

    public Resign(String authToken, int gameID, String visitorName) {
        super(authToken);
        this.visitorName = visitorName;
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getVisitorName() {
        return visitorName;
    }
}

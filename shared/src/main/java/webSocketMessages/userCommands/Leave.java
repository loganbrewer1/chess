package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {

    private final int gameID;
    private final String visitorName;

    public Leave(String authToken, int gameID, String visitorName) {
        super(authToken);
        this.visitorName = visitorName;
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getVisitorName() {
        return visitorName;
    }
}

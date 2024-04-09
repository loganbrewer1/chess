package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    private final int gameID;
    private final ChessMove move;
    private final String visitorName;

    public MakeMove(String authToken, int gameID, ChessMove move, String visitorName) {
        super(authToken);
        this.visitorName = visitorName;
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public String getVisitorName() {
        return visitorName;
    }
}

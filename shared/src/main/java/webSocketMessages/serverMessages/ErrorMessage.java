package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
    private final String errorMessage;

    public ErrorMessage(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return " " + errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

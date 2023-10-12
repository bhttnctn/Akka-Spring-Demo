package demo.web.model;

import javax.ws.rs.container.AsyncResponse;

public class Response {

    protected AsyncResponse asyncResponse;
    protected Long messageId;
    protected String message;
    protected int messageCounter;

    public Response(AsyncResponse asyncResponse, Long messageId, String message, int messageCounter) {
        this.asyncResponse = asyncResponse;
        this.messageId = messageId;
        this.message = message;
        this.messageCounter = messageCounter;
    }

    public AsyncResponse getAsyncResponse() {
        return asyncResponse;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageCounter() {
        return messageCounter;
    }
}
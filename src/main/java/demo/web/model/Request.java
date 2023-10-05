package demo.web.model;

import javax.ws.rs.container.AsyncResponse;

public class Request {

    private AsyncResponse asyncResponse;
    private Message message;

    public Request(AsyncResponse asyncResponse, Message message) {
        this.asyncResponse = asyncResponse;
        this.message = message;
    }

    public AsyncResponse getAsyncResponse() {
        return asyncResponse;
    }

    public Message getMessage() {
        return message;
    }
}

package demo.web.model;

import javax.ws.rs.container.AsyncResponse;

public class Message {

    protected AsyncResponse asyncResponse;
    protected Long messageId;

    public Message(AsyncResponse asyncResponse, Long messageId) {
        this.asyncResponse = asyncResponse;
        this.messageId = messageId;
    }

    public AsyncResponse getAsyncResponse() {
        return asyncResponse;
    }

    public Long getMessageId() {
        return messageId;
    }

    public static class StatefulMessage extends Message {

        public StatefulMessage(AsyncResponse asyncResponse, Long messageId) {
            super(asyncResponse, messageId);
        }
    }

    public static class StatelessMessage extends Message {

        public StatelessMessage(AsyncResponse asyncResponse, Long messageId) {
            super(asyncResponse, messageId);
        }
    }

    public static class GetStatefulMessage extends StatefulMessage {

        public GetStatefulMessage(AsyncResponse asyncResponse, Long messageId) {
            super(asyncResponse, messageId);
        }
    }

    public static class SetStatefulMessage extends StatefulMessage {

        private Request message;

        public SetStatefulMessage(AsyncResponse asyncResponse, Long messageId, Request message) {
            super(asyncResponse, messageId);
            this.message = message;
        }

        public Request getMessage() {
            return message;
        }
    }

    public static class DeleteStatefulMessage extends StatefulMessage {

        public DeleteStatefulMessage(AsyncResponse asyncResponse, Long messageId) {
            super(asyncResponse, messageId);
        }
    }

    public static class GetStatelessMessage extends StatelessMessage {

        public GetStatelessMessage(AsyncResponse asyncResponse, Long messageId) {
            super(asyncResponse, messageId);
        }
    }

    public static class SetStatelessMessage extends StatelessMessage {

        private Request message;

        public SetStatelessMessage(AsyncResponse asyncResponse, Long messageId, Request message) {
            super(asyncResponse, messageId);
            this.message = message;
        }

        public Request getMessage() {
            return message;
        }
    }
}
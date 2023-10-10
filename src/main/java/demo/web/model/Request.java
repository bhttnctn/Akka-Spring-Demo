package demo.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("payload")
    public String getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "StatefulRequest = {" + "payload='" + payload + '}';
    }
}

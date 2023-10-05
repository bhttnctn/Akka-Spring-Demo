package demo.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("payload")
    private String payload;
    @JsonProperty("id")
    private Long id;

    @JsonProperty("payload")
    public String getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" + "payload='" + payload + '\'' + ", id=" + id + '}';
    }
}
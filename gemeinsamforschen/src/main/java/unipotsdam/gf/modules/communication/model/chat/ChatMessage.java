package unipotsdam.gf.modules.communication.model.chat;

import java.time.Instant;

public class ChatMessage {

    String id;
    String message;
    Instant timestamp;
    String username;

    public ChatMessage() {}

    public ChatMessage(String id, String message, Instant timestamp, String username) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

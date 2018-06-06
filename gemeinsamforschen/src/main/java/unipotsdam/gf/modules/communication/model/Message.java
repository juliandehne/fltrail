package unipotsdam.gf.modules.communication.model;

public class Message {

    private String roomIdOrChannel;
    private String message;

    public Message() {}

    public Message(String roomIdOrChannel, String message) {
        this.roomIdOrChannel = roomIdOrChannel;
        this.message = message;
    }

    public String getRoomIdOrChannel() {
        return roomIdOrChannel;
    }

    public void setRoomIdOrChannel(String roomIdOrChannel) {
        this.roomIdOrChannel = roomIdOrChannel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "roomIdOrChannel='" + roomIdOrChannel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

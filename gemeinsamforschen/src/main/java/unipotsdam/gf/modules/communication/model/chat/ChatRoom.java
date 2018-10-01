package unipotsdam.gf.modules.communication.model.chat;

// TODO: never used at the moment by ICommunication, just context, maybe remove
public class ChatRoom {

    String id;
    String name;

    public ChatRoom() {}

    public ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

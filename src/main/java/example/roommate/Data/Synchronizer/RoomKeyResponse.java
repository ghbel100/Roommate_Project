package example.roommate.Data.Synchronizer;

import java.util.UUID;

public class RoomKeyResponse {
    private UUID key;
    private String owner;
    private String raum;
    private UUID room;

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public UUID getRoom() {
        return room;
    }

    public void setRoom(UUID room) {
        this.room = room;
    }

    public RoomKeyResponse(UUID key, String owner, String raum, UUID room) {
        this.key = key;
        this.owner = owner;
        this.raum = raum;
        this.room = room;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }
}

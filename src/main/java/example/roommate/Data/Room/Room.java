package example.roommate.Data.Room;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.Objects;
import java.util.UUID;


public class Room {
    @Id
    private Long roomId;
    @JsonProperty("raum")
    private String name;

    private UUID id;
    @PersistenceCreator
    public Room(Long roomId, String name, UUID id) {
        this.roomId = roomId;
        this.name = name;
        this.id=id;
    }
    public Room() {
    }

    public Room(String name, UUID id) {
        this(null, name,id);
    }
    public Room(String name) {
        this(null, name, UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId) && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, name);
    }


    public Long getRoomId() {
        return this.roomId;
    }
    public UUID getId() {
        return this.id;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

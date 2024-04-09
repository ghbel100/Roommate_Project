package example.roommate.Data.Room;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.UUID;

public class Key {
    @Id
    private Long keyId;

    private String owner;

    private UUID id;

    @PersistenceCreator
    public Key(Long keyId, String owner, UUID id) {
        this.keyId = keyId;
        this.owner = owner;
        this.id = id;
    }

    public Key(String owner, UUID id) {
        this(null,owner,id);
    }
    public Key(){}
    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

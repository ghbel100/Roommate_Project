package example.roommate.Data.Arbeitsplatz;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Arbeitsplatz {
    @Id
    private Long arbeitsplatzId;
    private String name;
    private Set<Ausstatung> ausstatungs;
    private Long roomId;

    @PersistenceCreator
    public Arbeitsplatz(Long arbeitsplatzId, String name, Set<Ausstatung> ausstatungs,
                        Long roomId) {
        this.arbeitsplatzId = arbeitsplatzId;
        this.name = name;
        this.ausstatungs=ausstatungs;
        this.roomId = roomId;
    }

    public Arbeitsplatz(String name,Long roomId) {
        this(null,name,new HashSet<>(),roomId);
    }

    public void addAusstatung(Ausstatung ausstatung) {

        ausstatungs.add(ausstatung);
    }
    public List<Ausstatung> getAusstatungs() {
        return ausstatungs.stream().toList();
    }

    public void deleteAusstatung(String ausstatungName){
        ausstatungs.removeIf(a -> a.getName().equals(ausstatungName));
    }



    public Long getArbeitsplatzId() {
        return arbeitsplatzId;
    }

    public void setArbeitsplatzId(Long arbeitsplatzId) {
        this.arbeitsplatzId = arbeitsplatzId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arbeitsplatz that = (Arbeitsplatz) o;
        return Objects.equals(arbeitsplatzId, that.arbeitsplatzId) && Objects.equals(name, that.name) && Objects.equals(ausstatungs, that.ausstatungs) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arbeitsplatzId, name, ausstatungs, roomId);
    }

    public List<String> getAusstatungsNames() {

            return ausstatungs.stream().map(Ausstatung::getName).toList();

    }
}

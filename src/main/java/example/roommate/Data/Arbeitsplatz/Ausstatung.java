package example.roommate.Data.Arbeitsplatz;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class Ausstatung {



    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ausstatung(String name) {
        this.name=name;
    }
}

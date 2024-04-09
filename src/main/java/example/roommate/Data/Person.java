package example.roommate.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.util.Objects;


public class Person {

    @Id
    private Long personId;


    private Integer githubId;



    private String username;
    @PersistenceCreator
    public Person(Long personId, Integer githubId, String username) {
        this.personId = personId;
        this.githubId = githubId;
        this.username = username;
    }

    public Person(Integer githubId, String username) {
       this(null,githubId,username);
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Integer getGithubId() {
        return githubId;
    }

    public void setGithubId(Integer githubId) {
        this.githubId = githubId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(githubId, person.githubId) && Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, githubId, username);
    }
}
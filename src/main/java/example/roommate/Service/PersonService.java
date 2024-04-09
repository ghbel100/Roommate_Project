package example.roommate.Service;

import example.roommate.Data.Person;
import example.roommate.Repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public void getOrCreatePerson(Integer githubId,String username) {
        personRepository.findByGithubId(githubId)
                .orElseGet(() -> {
                    Person person = new Person(githubId,username);
                    personRepository.save(person);
                    return person;
                });
    }

}

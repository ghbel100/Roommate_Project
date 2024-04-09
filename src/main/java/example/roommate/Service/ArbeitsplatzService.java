package example.roommate.Service;

import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import example.roommate.Repository.ArbeitsplatzRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbeitsplatzService {
    private final ArbeitsplatzRepository arbeitsplatzRepository;

    public ArbeitsplatzService(ArbeitsplatzRepository arbeitsplatzRepository) {
        this.arbeitsplatzRepository = arbeitsplatzRepository;
    }

    public List<Arbeitsplatz> getAllArbeitsplaetze() {
        return (List<Arbeitsplatz>) arbeitsplatzRepository.findAll();
    }

    public void addPlatz(Arbeitsplatz arbeitsplatz) {

        arbeitsplatzRepository.save(arbeitsplatz);

    }

    public List<String> getAllArbeitsplaetzeNames() {

        return arbeitsplatzRepository.getPlatzeNames();
    }

    public Arbeitsplatz getArbeitsplatzById(Long id) {
        return arbeitsplatzRepository.findById(id).orElse(null);
    }

    // For Testing purpose
    public Long addPlatzAndReturnID(Arbeitsplatz arbeitsplatz) {
        Arbeitsplatz saved = arbeitsplatzRepository.save(arbeitsplatz);
        return saved.getArbeitsplatzId();
    }

    public void updateArbeitsplatz(Arbeitsplatz arbeitsplatz) {
        arbeitsplatzRepository.save(arbeitsplatz);
    }

    public void deleteArbeitsplatz(Arbeitsplatz arbeitsplatz) {
        arbeitsplatzRepository.delete(arbeitsplatz);
    }

    public Arbeitsplatz deleteAusstatung(String ausstatungName, Long arbeitsplatzId) {
        Arbeitsplatz arbeitsplatz = getArbeitsplatzById(arbeitsplatzId);
        arbeitsplatz.deleteAusstatung(ausstatungName);
        return arbeitsplatz;
    }

public void deleteAllArbeitsplatzeByRoomId(Long roomId) {
    List<Arbeitsplatz> arbeitsplaetze = getAllArbeitsplaetze();
    for (Arbeitsplatz arbeitsplatz : arbeitsplaetze) {
        if (arbeitsplatz.getRoomId().equals(roomId)) {
            deleteArbeitsplatz(arbeitsplatz);
        }
    }
}
    public List<Arbeitsplatz> allArbeitsplatzeInRaumMitId(Long roomId){
        return arbeitsplatzRepository.findArbeitsplatzByRoomId(roomId);
    }

    public void deleteArbeitsplatzById(Long arbeitsplatzId) {
        arbeitsplatzRepository.deleteById(arbeitsplatzId);
    }

    public List<Arbeitsplatz> findAvailableArbeitsplatz(
                                                        LocalDate date,
                                                        LocalTime startTime,
                                                        LocalTime endTime){
        return arbeitsplatzRepository.findAvailableArbeitsplatz(date,startTime,endTime);
    }
    public List<Arbeitsplatz> filterArbeitsplatzByEquipment(List<Arbeitsplatz> availableArbeitsplatz, List<String> finalEquipment) {
        List<Arbeitsplatz> filteredArbeitsplatz = availableArbeitsplatz.stream()
                .filter(a -> a.getAusstatungsNames().containsAll(finalEquipment))
                .collect(Collectors.toList());
        availableArbeitsplatz =filteredArbeitsplatz;
        return availableArbeitsplatz;
    }
}

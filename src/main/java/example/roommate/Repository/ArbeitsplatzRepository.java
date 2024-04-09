package example.roommate.Repository;

import example.roommate.Data.Arbeitsplatz.Arbeitsplatz;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArbeitsplatzRepository extends CrudRepository<Arbeitsplatz, Long>  {
    @Query("select arbeitsplatz.name from arbeitsplatz")
    List<String> getPlatzeNames();

    List<Arbeitsplatz> findArbeitsplatzByRoomId(Long roomId);

    @Query("SELECT DISTINCT a.* FROM arbeitsplatz a " +
            "JOIN ausstatung aus ON a.arbeitsplatz_id = aus.arbeitsplatz " +
            "WHERE NOT EXISTS (" +
            "SELECT 1 FROM Reservation r " +
            "WHERE r.arbeitsplatz_id = a.arbeitsplatz_id " +
            "AND r.tag = :date " +
            "AND ((r.start_time >= :startTime AND r.start_time < :endTime) OR " +
            "(r.end_time > :startTime AND r.end_time <= :endTime) OR " +
            "(r.start_time < :startTime AND r.end_time > :endTime)))")
    List<Arbeitsplatz> findAvailableArbeitsplatz(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);


}

package example.roommate.Repository;

import example.roommate.Data.Reservation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM reservation r " +
            "WHERE r.arbeitsplatz_id = :arbeitsplatzId " +
            "AND r.tag = :day " +
            "AND ((r.start_time >= :startTime AND r.start_time < :endTime) OR " +
            "(r.end_time > :startTime AND r.end_time <= :endTime) OR " +
            "(r.start_time <= :startTime AND r.end_time >= :endTime))")
    Boolean reservationExists(
            @Param("arbeitsplatzId") Long arbeitsplatzId,
            @Param("day") LocalDate day,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}

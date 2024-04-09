package example.roommate.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    @Id
    private Long reservationId;
    private LocalDate tag;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long arbeitsplatzId;
    @PersistenceCreator
    public Reservation(Long reservationId, LocalDate tag, LocalTime startTime, LocalTime endTime, Long arbeitsplatzId) {
        this.reservationId = reservationId;
        this.tag = tag;
        this.endTime = endTime;
        this.startTime = startTime;
        this.arbeitsplatzId = arbeitsplatzId;
    }



    public Reservation(LocalDate tag, LocalTime startTime, LocalTime endTime, Long arbeitsplatzId) {
        this(null, tag,startTime,endTime,arbeitsplatzId);
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getTag() {
        return tag;
    }

    public void setTag(LocalDate tag) {
        this.tag = tag;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getArbeitsplatzId() {
        return arbeitsplatzId;
    }

    public void setArbeitsplatzId(Long arbeitsplatzId) {
        this.arbeitsplatzId = arbeitsplatzId;
    }
}
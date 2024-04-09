package example.roommate.Service;

import example.roommate.Data.Reservation;
import example.roommate.Repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private final ReservationRepository reservationRepository;

    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }


    public Boolean checkReservationExists(Reservation reservation) {
        return reservationRepository.reservationExists(reservation.getArbeitsplatzId(),
                                                reservation.getTag(),
                                                reservation.getStartTime(),
                                                reservation.getEndTime());
    }
}

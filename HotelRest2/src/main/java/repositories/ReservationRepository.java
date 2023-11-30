package repositories;

import models.Adresse;
import models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation,Long> {
}

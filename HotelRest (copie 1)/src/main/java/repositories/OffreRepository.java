package repositories;

import models.Adresse;
import models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreRepository  extends JpaRepository<Offre,Long> {
}

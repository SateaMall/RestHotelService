package repositories;

import models.Adresse;
import models.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse,Long> {
}

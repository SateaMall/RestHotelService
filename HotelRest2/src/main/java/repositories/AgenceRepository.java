package repositories;

import models.Adresse;
import models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence,Long> {
}

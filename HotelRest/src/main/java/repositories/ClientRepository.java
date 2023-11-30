package repositories;

import models.Adresse;
import models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    <T> Optional<T> findByNomAndPrenomAndCreditCard(String nomClient, String prenomClient, int creditCard);
}

package controllers;

import exceptions.LoginIdentificationBadException;
import exceptions.NotFoundException;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HotelController {
    private static final String uri="Hotelservice/api";
    @Autowired
    private HotelRepository Hrepository;
    @Autowired
    private ChambreRepository Crepository;
    @Autowired
    private AdresseRepository ADrepository;
    @Autowired
    private OffreRepository Orepository;
    @Autowired
    private ClientRepository Cltrepository;
    @Autowired
    private ReservationRepository Rrepository;
    private Hotel hotel;


    /**************Service 1 ************/
//Test: http://localhost:8080/Hotelservice/api/disponibilites?id=0&mdp=0000&dateDebut=2023-01-01&dateFin=2023-01-10&nombrePersonnes=2
    @GetMapping(uri+"/disponibilites")
    public List<Offre> getDisponibilites(@RequestParam int id,
                                         @RequestParam String mdp,
                                         @RequestParam LocalDate dateDebut,
                                         @RequestParam LocalDate dateFin,
                                         @RequestParam int nombrePersonnes) throws LoginIdentificationBadException {
        if(!signIn(id,mdp)) {throw new LoginIdentificationBadException(); }

        // Trouver les chambres disponibles
        List<Chambre> chambresDisponibles = Crepository.findAll().stream()
                .filter(chambre -> chambre.getCapacite() >= nombrePersonnes)
                .toList();

        // Créer des offres :
        List<Offre> offres = new ArrayList<>();
        for (Chambre chambre : chambresDisponibles) {
            if(dateValide(chambre,dateDebut,dateFin)) {
                Offre offre = new Offre(chambre.getCapacite(), dateDebut, dateFin, chambre.getPrix_base(), chambre.getId(), hotel.getId(), chambre.getImage(), hotel.getEtoiles(), hotel.getNom(),hotel.getAdresse().getVille());
                offres.add(offre);
            }
        }
        Orepository.saveAll(offres);
        return offres;
    }

    /**************Service 2 ************/
    //Test: http://localhost:8080/Hotelservice/api/reserver?offreId=1&nomClient=Dupont&prenomClient=Jean&portable=0606060606&creditCard=123456789
    @GetMapping(uri + "/reserver")
    public int reserver(@RequestParam Long offreId,
                                           @RequestParam String nomClient,
                                           @RequestParam String prenomClient,
                                           @RequestParam String portable,
                                           @RequestParam int creditCard) {
        try {
            Offre offre = Orepository.findById(offreId)
                    .orElseThrow(() -> new NotFoundException("Offre non trouvée"));

            // Création du client
            Client client = (Client) Cltrepository.findByNomAndPrenomAndCreditCard(nomClient, prenomClient, creditCard)
                    .orElseGet(() -> {
                        return Cltrepository.save(new Client(nomClient, prenomClient, portable, creditCard));
                    });

            Chambre chambre = Crepository.findById(offre.getChambreId())
                    .orElseThrow(() -> new NotFoundException("Chambre non trouvée"));
            // Création de la réservation
            Reservation reservation = new Reservation(offre.getDate_arrive(), offre.getDate_depart(), chambre, client);
            Rrepository.save(reservation);
            chambre.addReservation(reservation);
            Orepository.deleteById(offre.getId());
            return reservation.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @GetMapping(uri+"/Chambre/{id}")
    public Chambre getChambreById(@PathVariable long id) throws NotFoundException {
        return Crepository.findById(id).orElseThrow(NotFoundException::new);
    }
    @GetMapping(uri+"/test")
    public String test() {
        return "Service is up";
    }


    /***********	METHODES  ***********/
    public boolean signIn(int id, String mdp) {
        hotel= Hrepository.getReferenceById(0L);
        List<Agence> agences= hotel.getAgences();
        for(Agence agence: agences) {
            if (agence.getId() == id) {
                if (agence.getMdp().equals(mdp)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean  dateValide(Chambre c, LocalDate arv, LocalDate dep) {
        for (Reservation r: c.getReservations()){
            if(r.dateOverlap(dep,arv)){
                return false;
            }
        }
        return true;
    }
}

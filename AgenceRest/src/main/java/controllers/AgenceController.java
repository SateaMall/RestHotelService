package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.Agence;
import models.Hotel;
import models.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import repositories.AgenceRepository;
import repositories.HotelRepository;
import repositories.OffreRepository;

import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/AgenceService")
public class AgenceController {
    @Autowired
    private RestTemplate proxy;
    @Autowired
    private HotelRepository Hrepository;
    @Autowired
    private AgenceRepository Arepository;
    @Autowired
    private OffreRepository Orepository;
    private Agence agence;


    /************** Service 1 ************/
    @GetMapping("/api/comparable")
    public List<Offre> comparable(@RequestParam LocalDate dateDebut,
                                  @RequestParam LocalDate dateFin,
                                  @RequestParam int nombrePersonnes,
                                  @RequestParam String ville,
                                  @RequestParam int etoile) throws JsonProcessingException {
        List <Offre> offres = invokeOffres(dateDebut,dateFin,nombrePersonnes);
        List <Offre> offreFiltre = new ArrayList<>();
        for (Offre offre: offres){
            if(offre.getVille().equalsIgnoreCase(ville)&&offre.getEtoileHotel()>=etoile){
                offreFiltre.add(offre);
                offre.setPrix(agence.reducPrix(offre.getPrix()));
                offre.setNomAgence(agence.getNom());
                offre.setAgenceId(agence.getId());
            }
        }
        Orepository.saveAll(offreFiltre);
        return offreFiltre; //control the info sent OR not
    }

//Test : http://localhost:8081/AgenceService/api/comparable?dateDebut=2023-01-01&dateFin=2023-01-10&nombrePersonnes=5&ville=cologne&etoile=4
    public List<Offre> invokeOffres(LocalDate dateDebut, LocalDate dateFin, int nbrPersonne) throws JsonProcessingException {
        agence =Arepository.getReferenceById(0L);
        List<Hotel> hotels = Hrepository.findAll();
        List<Offre> offres= new ArrayList<>();
        for(Hotel hotel: hotels) {
            String url = hotel.getS1() + "id=" + agence.getId() + "&mdp=" + agence.getMdp() + "&dateDebut=" + dateDebut.toString() + "&dateFin=" + dateFin.toString() + "&nombrePersonnes=" + nbrPersonne;
            String response = proxy.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());//Pour configurer l'objetMapprer pour qu'il utiliser le module qui prend en charge le localdate
            try {
                List<Offre> offresTemp = objectMapper.readValue(response, new TypeReference<List<Offre>>() {});
                for(Offre offre: offresTemp){
                    offre.setHotelId(hotel.getId());
                }
                offres.addAll(offresTemp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return offres;
    }


    /************** Service 2 ************/
    //Test:http://localhost:8081/AgenceService/api/reserver?offreId=1&nomClient=ALMALLOUHI&prenomClient=Satea&portable=0753406179&creditCard=654233541
    @GetMapping( "/api/reserver")
    public int reserver(@RequestParam Long offreId,
                        @RequestParam String nomClient,
                        @RequestParam String prenomClient,
                        @RequestParam String portable,
                        @RequestParam int creditCard) {
        //à faire!
        Offre offre= Orepository.getReferenceById(offreId);
        Hotel hotel=Hrepository.getReferenceById(offre.getHotelId());
        String url = hotel.getS2() +"offreId="+offre.getId()+"&nomClient="+nomClient+"&prenomClient="+prenomClient+"&portable="+portable+"&creditCard="+creditCard;
        int response = proxy.getForObject(url, int.class);
        return response;

    }

}

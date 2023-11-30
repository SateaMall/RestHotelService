package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.Agence;
import models.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import repositories.AgenceRepository;
import repositories.OffreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private OffreRepository Orepository;
    @Autowired
    private AgenceRepository Arepository;
    @Autowired
    private RestTemplate proxy;
    private Offre offreChoix;


    @GetMapping("/Accueil") //Afficher la page accueil
    public String getPreferences() {
        System.out.println( "getPreferences!");
        return "Accueil";
    }
    @PostMapping("/Accueil") //Traiter les infos insérés
    public String afficherOffres(@RequestParam LocalDate dateDebut,
                                 @RequestParam int etoile,
                                 @RequestParam LocalDate dateFin,
                                 @RequestParam int nombrePersonnes,
                                 @RequestParam String ville,
                                 Model model,
                                 RedirectAttributes redirectAttributes) throws JsonProcessingException {
        System.out.println( "afficherOffres!");
        if(dateDebut.isAfter(dateFin) || dateDebut.isBefore(LocalDate.now())){
            redirectAttributes.addFlashAttribute("error", "Date is invalid");
            return "redirect:/Accueil";
        }
        List<Offre> offres =  comparable(dateDebut,dateFin,nombrePersonnes,ville,etoile);
        redirectAttributes.addFlashAttribute("offres", offres);
        return "redirect:/AfficherOffres";
    }

    @GetMapping("/AfficherOffres") //Afficher la page AfficherOffres
    public String getOffres( Model model) {

        System.out.println("getOffres");
        return "AfficherOffres";
    }
    @GetMapping("/AfficherOffre")
    public String reserver( @RequestParam Long offreId){ //plustard proteger request body
        offreChoix= Orepository.getReferenceById(offreId);
        System.out.println("reserver: "+offreChoix.getId());
        return "redirect:/Reservation";
    }
    //Les attributs flash sont disponibles uniquement après la redirection immédiate.
    @GetMapping("/Reservation") //Afficher la page reservation
    public String getReservation(){
        System.out.println("reserver GET");
        return "Reservation";
    }
    @PostMapping("/Reservation")
    public String getInformation(@RequestParam String nom, @RequestParam String prenom, @RequestParam String portable, @RequestParam int creditCard, RedirectAttributes redirectAttributes,Model model){
        System.out.println("getInformation");
        int id= reserver(offreChoix.getId(), nom, prenom, portable, creditCard);
        if(id==-1){
            return "redirect:/Problem";
        }
        redirectAttributes.addFlashAttribute("id", id); //Les attributs flash sont disponibles uniquement après la redirection immédiate.
        return "redirect:/Valider";
    }

    @GetMapping("/Valider") //Afficher la page reservation
    public String getValider( Model model){
        System.out.println("Valider");
        return "Valider";
    }
    @GetMapping("/Problem") //Afficher la page reservation
    public String getProblem( Model model){
        System.out.println("Problem");
        return "Problem";
    }

/******* METHODES *******/
//, double prixMin, double prixMax
public List<Offre> comparable(LocalDate dateDebut, LocalDate dateFin, int nombrePersonnes, String ville, int etoile) throws JsonProcessingException {
    List <Agence> agences= Arepository.findAll();
    List<Offre> offres= new ArrayList<>();
    for(Agence agence: agences) {
        String url = agence.getS1() + "&dateDebut=" + dateDebut.toString() + "&dateFin=" + dateFin.toString() + "&nombrePersonnes=" + nombrePersonnes+ "&ville="+ ville+"&etoile="+etoile;
        String response = proxy.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());//Pour configurer l'objetMapprer pour qu'il utiliser le module qui prend en charge le localdate
        try {
            offres.addAll(objectMapper.readValue(response, new TypeReference<List<Offre>>() {}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Offre offre: offres){
            offre.setAgenceId(agence.getId());
        }

    }
  /*  List <Offre> offreFiltre = new ArrayList<>();
    for (Offre offre: offres){
        if(offre.getPrix()<=prixMax&&offre.getPrix()>=prixMin){
            offreFiltre.add(offre);
        }
    }*/
    Orepository.saveAll(offres);
    return offres; //control the info sent OR not
}
    public int reserver( Long offreId, String nomClient, String prenomClient, String portable, int creditCard) {
        Offre offre= Orepository.getReferenceById(offreId);
        Agence agence=Arepository.getReferenceById(offre.getAgenceId());
        String url = agence.getS2() +"offreId="+offre.getId()+"&nomClient="+nomClient+"&prenomClient="+prenomClient+"&portable="+portable+"&creditCard="+creditCard;
        int response = proxy.getForObject(url, int.class);
        return response;

    }


}

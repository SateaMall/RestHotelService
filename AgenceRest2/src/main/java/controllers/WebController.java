package controllers;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
        import models.Agence;
        import models.Hotel;
        import models.Offre;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;
        import repositories.AgenceRepository;
        import repositories.HotelRepository;
        import repositories.OffreRepository;

        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.List;

@Controller
public class WebController {
    @Autowired
    private RestTemplate proxy;
    @Autowired
    private HotelRepository Hrepository;
    @Autowired
    private AgenceRepository Arepository;
    @Autowired
    private OffreRepository Orepository;
    private Agence agence;
    private Offre offreChoix;

    @GetMapping("/Accueil") //Afficher la page accueil
    public String getPreferences( Model model) {
        System.out.println( "getPreferences!");
        agence =Arepository.getReferenceById(1L);
        model.addAttribute("agence",agence );
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

    /************** METHODES TO USE  ************/
    public List<Offre> invokeOffres(LocalDate dateDebut, LocalDate dateFin, int nbrPersonne) throws JsonProcessingException {
        agence =Arepository.getReferenceById(1L);
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

    public List<Offre> comparable(LocalDate dateDebut, LocalDate dateFin, int nombrePersonnes, String ville, int etoile) throws JsonProcessingException {
        List <Offre> offres = invokeOffres(dateDebut,dateFin,nombrePersonnes);
        List <Offre> offreFiltre = new ArrayList<>();
        for (Offre offre: offres){
            if(offre.getVille().equalsIgnoreCase(ville)&&offre.getEtoileHotel()>=etoile){
                offreFiltre.add(offre);
                offre.setPrix(Math.round(agence.reducPrix(offre.getPrix())*100.0)/100.0);
            }
        }
        Orepository.saveAll(offreFiltre);
        return offreFiltre; //control the info sent OR not
    }

    /************** Service 2 ************/

    public int reserver(Long offreId, String nomClient, String prenomClient, String portable, int creditCard) {
        //à faire!
        Offre offre= Orepository.getReferenceById(offreId);
        Hotel hotel=Hrepository.getReferenceById(offre.getHotelId());
        String url = hotel.getS2() +"offreId="+offre.getId()+"&nomClient="+nomClient+"&prenomClient="+prenomClient+"&portable="+portable+"&creditCard="+creditCard;
        int response = proxy.getForObject(url, int.class);
        return response;

    }
}

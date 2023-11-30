package data;

import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.*;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HotelData {
   private Adresse adresse;
   private  Hotel hotel ;
   private Client client;
   private List<Chambre> chambres;
    /* ATTRIBUTES */
    private final Logger logger = LoggerFactory.getLogger(HotelData.class);

    @Bean
    public CommandLineRunner initDatabaseAdresse(AdresseRepository repository){
        adresse = new Adresse("Allemagne", "Cologne", "Corniche El Manara", "Riviera","6546554,654665 6546654,6544654 ", 1109);
        return args -> {
            logger.info("preloading database with " + repository.save(adresse));
        };
    }
    @Bean
    public CommandLineRunner initDatabaseHotel(HotelRepository repository){
        hotel = new Hotel("Riviera", "Lux",3, adresse,0L);
        return args -> {
            logger.info("preloading database with " + repository.save(hotel));
        };
    }
    @Bean
    public CommandLineRunner initDatabaseChambre(ChambreRepository repository) throws IOException {
        chambres = new ArrayList<Chambre>();

        String woodB="Photos/woodB.jpg";
        String CheapB="Photos/CheapB.jpg";
        String redB="Photos/redB.jpg";
        String AlienB="Photos/AlienB.jpg";
        String ParisB="Photos/ParisB.jpg";
        String normalB="Photos/normalB.jpg";
        Chambre c1= new Chambre (1, 4, 1500, hotel,woodB);  hotel.addChambre(c1);
        chambres.add(c1);
        Chambre c2= new Chambre (2, 4, 1500,  hotel,CheapB); hotel.addChambre(c2);
        chambres.add(c2);
        Chambre c4= new Chambre (4, 2, 500,  hotel,redB); hotel.addChambre(c4);
        chambres.add(c4);
        Chambre c5= new Chambre (5, 2, 500,  hotel,AlienB); hotel.addChambre(c5);
        chambres.add(c5);
        Chambre c8= new Chambre (8, 1, 850,  hotel,ParisB); hotel.addChambre(c8);
        chambres.add(c8);
        Chambre c10=new Chambre (10,1, 850,  hotel,normalB); hotel.addChambre(c10);
        chambres.add(c10);
        return args -> {
            logger.info("preloading database with " + repository.save(c1));
            logger.info("preloading database with " + repository.save(c2));
            logger.info("preloading database with " + repository.save(c4));
            logger.info("preloading database with " + repository.save(c5));
            logger.info("preloading database with " + repository.save(c8));
            logger.info("preloading database with " + repository.save(c10));
        };
    }

    @Bean
    public CommandLineRunner initDatabaseClient(ClientRepository repository){
        client= new Client("almallouhi","mohamad satea", "07565465486", 65465465);
        return args -> {
            logger.info("preloading database with " + repository.save(client));
        };
    }

@Bean
    public CommandLineRunner initDatabaseAgence(AgenceRepository repository) {
        Agence agence =  new  Agence("Elbat", 0L, "0000",hotel);
        Agence agence1 =  new  Agence("Chepas", 1L, "0000",hotel);

        return args -> {
            logger.info("preloading database with " + repository.save(agence));
            logger.info("preloading database with " + repository.save(agence1));
            hotel.addAgences(agence);
            hotel.addAgences(agence1);
        };
    }

}

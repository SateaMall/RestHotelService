package data;

import models.Agence;
import models.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.AgenceRepository;
import repositories.HotelRepository;

@Configuration
public class AgenceData {


    @Autowired
    private AgenceRepository Arepository;
    @Autowired
    private HotelRepository Hrepository;
    private Agence agence;
    private final Logger logger = LoggerFactory.getLogger(AgenceData.class);

    @Bean
    public CommandLineRunner initDatabaseAgence(AgenceRepository repository){
        agence= new Agence("ElBat", 10,0, "0000");

        return args -> {
            logger.info("preloading database with " + repository.save(agence));
        };
    }
    @Bean
    public CommandLineRunner initDatabaseHotel(HotelRepository repository){
        Hotel hotel = new Hotel("4Seasons", "http://localhost:9000/Hotelservice/api/disponibilites?","http://localhost:9000/Hotelservice/api/reserver?");
        Hotel hotel1 = new Hotel("Riviera", "http://localhost:9001/Hotelservice/api/disponibilites?","http://localhost:9001/Hotelservice/api/reserver?");
        agence.addhotel(hotel);
        agence.addhotel(hotel1);
        hotel.setAgence(agence);
        hotel1.setAgence(agence);
        return args -> {
            logger.info("preloading database with " + repository.save(hotel));
            logger.info("preloading database with " + repository.save(hotel1));
        };
    }


}

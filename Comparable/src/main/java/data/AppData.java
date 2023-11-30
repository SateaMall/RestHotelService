package data;

import models.Agence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.AgenceRepository;
import repositories.OffreRepository;

@Configuration
public class AppData {
    @Autowired
    private AgenceRepository Arepository;
    @Autowired
    private OffreRepository Orepository;


    private final Logger logger = LoggerFactory.getLogger(AppData.class);


    @Bean
    public CommandLineRunner initDatabaseAgence(AgenceRepository repository){


        return args -> {
            logger.info("preloading database with " + repository.save(new Agence("ElBat",0, "http://localhost:8000/AgenceService/api/comparable?","http://localhost:8000/AgenceService/api/reserver?")));
            logger.info("preloading database with " + repository.save(new Agence("Chaipa",1, "http://localhost:8001/AgenceService/api/comparable?","http://localhost:8001/AgenceService/api/reserver?")));
        };
    }


}

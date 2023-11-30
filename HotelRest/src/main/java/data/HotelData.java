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
        adresse = new Adresse("Germany", "Cologne", "570 Route de Ganges", "Hotel de 4 seisons","654654,65465 654654,654654 ", 34090);
        return args -> {
            logger.info("preloading database with " + repository.save(adresse));
        };
    }
    @Bean
    public CommandLineRunner initDatabaseHotel(HotelRepository repository){
        hotel = new Hotel("4 Seasons", "Lux",5, adresse,1L);
        return args -> {
            logger.info("preloading database with " + repository.save(hotel));
        };
    }
    @Bean
    public CommandLineRunner initDatabaseChambre(ChambreRepository repository) throws IOException {
        chambres = new ArrayList<Chambre>();
        String badHotel="Photos/badHotel.jpg";
        String mediumHotel="Photos/mediumHotel.jpg";
        String goodHotel="Photos/goodHotel.jpg";
        Chambre c1= new Chambre (1, 4, 1000, hotel,goodHotel);  hotel.addChambre(c1);
        chambres.add(c1);
        Chambre c2= new Chambre (2, 4, 1000,  hotel,goodHotel); hotel.addChambre(c2);
        chambres.add(c2);
        Chambre c3= new Chambre (3, 2, 200,  hotel,mediumHotel); hotel.addChambre(c3);
        chambres.add(c3);
        Chambre c4= new Chambre (4, 2, 200,  hotel,mediumHotel); hotel.addChambre(c4);
        chambres.add(c4);
        Chambre c5= new Chambre (5, 2, 200,  hotel,mediumHotel); hotel.addChambre(c5);
        chambres.add(c5);
        Chambre c6= new Chambre (6, 1, 150,  hotel,badHotel); hotel.addChambre(c6);
        chambres.add(c6);
        Chambre c7= new Chambre (7, 1, 150,  hotel,badHotel); hotel.addChambre(c7);
        chambres.add(c7);
        Chambre c8= new Chambre (8, 1, 150,  hotel,badHotel); hotel.addChambre(c8);
        chambres.add(c8);
        Chambre c9= new Chambre (9, 1, 150,  hotel,badHotel); hotel.addChambre(c9);
        chambres.add(c9);
        Chambre c10=new Chambre (10,1, 150,  hotel,badHotel); hotel.addChambre(c10);
        chambres.add(c10);
        return args -> {
            logger.info("preloading database with " + repository.save(c1));
            logger.info("preloading database with " + repository.save(c2));
            logger.info("preloading database with " + repository.save(c3));
            logger.info("preloading database with " + repository.save(c4));
            logger.info("preloading database with " + repository.save(c5));
            logger.info("preloading database with " + repository.save(c6));
            logger.info("preloading database with " + repository.save(c7));
            logger.info("preloading database with " + repository.save(c8));
            logger.info("preloading database with " + repository.save(c9));
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
    public CommandLineRunner initDatabaseReservation(ReservationRepository repository){

        Reservation r1 = new Reservation( LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 5),    chambres.get(0),  client);
        Reservation r2 = new Reservation( LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 10),    chambres.get(0),  client);
        Reservation r3 = new Reservation( LocalDate.of(2023, 11, 11), LocalDate.of(2023, 11, 15),    chambres.get(3),  client);
        Reservation r4 = new Reservation( LocalDate.of(2023, 11, 16), LocalDate.of(2023, 11, 20),    chambres.get(3),  client);
        Reservation r5 = new Reservation( LocalDate.of(2023, 11, 21), LocalDate.of(2023, 11, 25),    chambres.get(3),  client);

        chambres.get(3).addReservation(r1);
        chambres.get(3).addReservation(r2);
        chambres.get(3).addReservation(r3);
        chambres.get(6).addReservation(r4);
        chambres.get(6).addReservation(r5);

        return args -> {
            logger.info("preloading database with " + repository.save(r1));
            logger.info("preloading database with " + repository.save(r2));
            logger.info("preloading database with " + repository.save(r3));
            logger.info("preloading database with " + repository.save(r4));
            logger.info("preloading database with " + repository.save(r5));
        };
    }
@Bean
    public CommandLineRunner initDatabaseAgence(AgenceRepository repository) {
        Agence agence =  new  Agence("Sham", 0L, "0000",hotel);

        return args -> {
            logger.info("preloading database with " + repository.save(agence));
            hotel.addAgences(agence);
        };
    }

}

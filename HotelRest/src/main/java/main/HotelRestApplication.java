package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"models"})
@EnableJpaRepositories(basePackages = {"repositories"})
@SpringBootApplication(scanBasePackages = {"data","exceptions","controllers"})
public class HotelRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRestApplication.class, args);
	}

}

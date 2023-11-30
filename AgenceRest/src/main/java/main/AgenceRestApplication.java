package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"models"})
@EnableJpaRepositories(basePackages = {"repositories"})
@SpringBootApplication(scanBasePackages = {"data","exceptions","controllers","Client"})
public class AgenceRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgenceRestApplication.class, args);
    }

}

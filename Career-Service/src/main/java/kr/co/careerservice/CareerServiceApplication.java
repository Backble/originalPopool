package kr.co.careerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CareerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerServiceApplication.class, args);
    }

}

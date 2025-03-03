package org.travelagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TravelAgencyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAgencyAppApplication.class, args);
	}

}
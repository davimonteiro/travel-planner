package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BikeRentalApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BikeRentalApplication.class, args);
		SpringApplication springApplication = new SpringApplication(BikeRentalApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("bike-rental.pid"));
        springApplication.run(args);
	}
}

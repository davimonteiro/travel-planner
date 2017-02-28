package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarRentalApplication {

	public static void main(String[] args) {
		//SpringApplication.run(CarRentalApplication.class, args);
		SpringApplication springApplication = new SpringApplication(CarRentalApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("car-rental.pid"));
        springApplication.run(args);
	}
}

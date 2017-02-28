package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FlightReservationApplication {
	
	public static void main(String[] args) {
		//SpringApplication.run(FlightReservationApplication.class, args);
		SpringApplication springApplication = new SpringApplication(FlightReservationApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("flight-reservation.pid"));
        springApplication.run(args);
	}
}

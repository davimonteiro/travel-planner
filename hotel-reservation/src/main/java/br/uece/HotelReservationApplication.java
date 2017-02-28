package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HotelReservationApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HotelReservationApplication.class, args);
		SpringApplication springApplication = new SpringApplication(HotelReservationApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("hotel-reservation.pid"));
        springApplication.run(args);
	}
}

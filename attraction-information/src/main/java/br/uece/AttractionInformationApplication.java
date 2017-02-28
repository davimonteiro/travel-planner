package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AttractionInformationApplication {

	public static void main(String[] args) {
		//SpringApplication.run(AttractionInformationApplication.class, args);
		SpringApplication springApplication = new SpringApplication(AttractionInformationApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("attraction-information.pid"));
        springApplication.run(args);
	}
}

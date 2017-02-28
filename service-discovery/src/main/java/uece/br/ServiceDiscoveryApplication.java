package uece.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ServiceDiscoveryApplication.class, args);
		SpringApplication eurekaServer = new SpringApplication(ServiceDiscoveryApplication.class);
		eurekaServer.addListeners(new ApplicationPidFileWriter("service-discovery.pid"));
        eurekaServer.run();
	}
}

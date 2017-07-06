package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableEurekaClient
@EnableAsync
public class TravelPlannerWorkflowApplication {

	public static void main(String[] args) throws Exception {
        SpringApplication.run(TravelPlannerWorkflowApplication.class, args);
    }

}

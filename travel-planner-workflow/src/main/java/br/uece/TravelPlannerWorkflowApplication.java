package br.uece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TravelPlannerWorkflowApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TravelPlannerWorkflowApplication.class, args);
		SpringApplication springApplication = new SpringApplication(TravelPlannerWorkflowApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("travel-planner-workflow.pid"));
        springApplication.run(args);
		
	}
}

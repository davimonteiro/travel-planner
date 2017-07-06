package br.uece.beethoven.api;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import br.uece.beethoven.engine.core.ActorPath;
import br.uece.beethoven.engine.core.DeciderActor;
import br.uece.beethoven.engine.dsl.EventType;
import br.uece.beethoven.engine.dsl.Workflow;
import br.uece.beethoven.logic.dsl.EventHandler;
import br.uece.beethoven.repository.TaskRepository;
import br.uece.beethoven.repository.WorkflowRepository;
import br.uece.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static br.uece.beethoven.engine.dsl.HttpActionBuilder.request;
import static br.uece.beethoven.engine.dsl.TaskBuilder.task;
import static br.uece.beethoven.engine.dsl.WorkflowBuilder.workflow;
import static br.uece.beethoven.logic.dsl.CommandBuilder.startTask;
import static br.uece.beethoven.logic.dsl.ConditionBuilder.condition;
import static br.uece.beethoven.logic.dsl.EventDslBuilder.eventDsl;
import static java.util.Arrays.asList;

@RestController
@RequestMapping("workflows")
public class WorkflowResource {



    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ActorSystem actorSystem;

    @GetMapping(value = "start")
    public void start() throws Exception {

        BikeReservation bikeReservation = BikeReservation.builder()
                .client(BikeClient.builder().id(1L).name("Davi").build())
                .bike(Bike.builder().id(2L).name("peed").build())
                .date(LocalDate.now()).build();

        String bikeReservationJson = mapper.writeValueAsString(bikeReservation);


        CarReservation carReservation = CarReservation.builder()
                .client(CarClient.builder().id(1L).name("Davi").build())
                .car(Car.builder().id(1L).name("Fusca").build())
                .date(LocalDate.now()).build();

        String carReservationJson = mapper.writeValueAsString(carReservation);


        Workflow workflow =

        workflow().name("TravelPlanner")
            .tasks(
                task().name("searchAttractions").doIt(request().get("http://attraction-information/attractions").build()),
                task().name("searchFlights").doIt(request().get("http://flight-reservation/flights").build()),
                task().name("searchHotels").doIt(request().get("http://hotel-reservation/hotels").build()),
                task().name("searchBikes").doIt(request().get("http://bike-rental/reservations").build()),
                task().name("rentBike").doIt(request().post("http://bike-rental/reservations").build()),
                task().name("searchCars").doIt(request().get("http://car-rental/reservations").build()),
                task().name("rentCar").doIt(request().post("http://car-rental/reservations").build()))

            .eventHandlers(
                    eventDsl().on(EventType.WORKFLOW_STARTED)
                            .when(condition().workflowNameEqualsTo("TravelPlanner"))
                            .then(startTask("searchAttractions")),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("searchAttractions"))
                            .then(startTask("searchFlights")),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("searchFlights"))
                            .then(startTask("searchHotels")),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("searchHotels"))
                            .then(startTask("searchBikes")),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("searchBikes"))
                            .then(startTask("rentBike")),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("rentBike"))
                            .then(startTask("searchCars", bikeReservationJson)),
                    eventDsl().on(EventType.TASK_COMPLETED)
                            .when(condition().taskNameEqualsTo("searchCars"))
                            .then(startTask("rentCar", carReservationJson)));

        List<EventHandler> events =
                asList(eventDsl().on(EventType.WORKFLOW_STARTED)
                                .when(condition().workflowNameEqualsTo("TravelPlanner"))
                                .then(startTask("searchAttractions")),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("searchAttractions"))
                                .then(startTask("searchFlights")),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("searchFlights"))
                                .then(startTask("searchHotels")),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("searchHotels"))
                                .then(startTask("searchBikes")),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("searchBikes"))
                                .then(startTask("rentBike")),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("rentBike"))
                                .then(startTask("searchCars", bikeReservationJson)),
                        eventDsl().on(EventType.TASK_COMPLETED)
                                .when(condition().taskNameEqualsTo("searchCars"))
                                .then(startTask("rentCar", carReservationJson))
                );


        workflowRepository.save(workflow);
        taskRepository.save(workflow.getTasks());

        // Log
        System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(workflow));
        System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events));
        System.err.println(workflow);
        System.err.println(events);
        System.err.println(actorSystem.guardian().path());


        actorSystem.actorSelection(ActorPath.DECIDER_ACTOR).tell(new DeciderActor.WorkflowScheduledEvent("TravelPlanner"), ActorRef.noSender());

    }

}

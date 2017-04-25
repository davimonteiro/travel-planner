package br.uece.beethoven.api;

import br.uece.beethoven.engine.Task;
import br.uece.beethoven.engine.Workflow;
import br.uece.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static br.uece.beethoven.engine.HttpActionBuilder.request;
import static br.uece.beethoven.engine.TaskBuilder.task;
import static br.uece.beethoven.engine.WorkflowBuilder.workflow;
import static br.uece.beethoven.logic.dsl.ConditionBuilder.condition;
import static br.uece.beethoven.logic.dsl.DslActionBuilder.startTask;
import static br.uece.beethoven.logic.dsl.EventDslBuilder.eventDsl;

@RestController
@RequestMapping("workflows")
public class WorkflowResource {

    @Autowired
    private ObjectMapper mapper;

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



        Workflow workflow = workflow().name("TravelPlanner").tasks(
                task().name("searchAttractions").doIt(request().get("http://attraction-information/attractions").build()),
                task().name("searchFlights").doIt(request().get("http://flight-reservation/flights").build()),
                task().name("searchHotels").doIt(request().get("http://hotel-reservation/hotels").build()),
                task().name("searchBikes").doIt(request().get("http://bike-rental/reservations").build()),
                task().name("rentBike").doIt(request().post("http://bike-rental/reservations").build()),
                task().name("searchCars").doIt(request().get("http://car-rental/reservations").build()),
                task().name("rentCar").doIt(request().post("http://car-rental/reservations").build()));

        eventDsl().on(Workflow.WorkflowEvent.WORKFLOW_SCHEDULED)
                .when(condition().workflowNameEqualsTo("TravelPlanner"))
                .then(startTask("searchAttractions"));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("searchAttractions"))
                .then(startTask("searchFlights"));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("searchFlights"))
                .then(startTask("searchHotels"));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("searchHotels"))
                .then(startTask("searchBikes"));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("searchBikes"))
                .then(startTask("rentBike"));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("rentBike"))
                .then(startTask("searchCars", bikeReservationJson));

        eventDsl().on(Task.TaskEvent.TASK_COMPLETED)
                .when(condition().taskNameEqualsTo("searchCars"))
                .then(startTask("rentCar", carReservationJson));

        // events and commands

        System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(workflow));

    }

}

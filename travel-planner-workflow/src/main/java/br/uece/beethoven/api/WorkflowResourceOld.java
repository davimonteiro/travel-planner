package br.uece.beethoven.api;

import br.uece.domain.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static br.uece.beethoven.logic.dsl.DslActionBuilder.startTask;

@RestController
@RequestMapping("workflows")
public class WorkflowResourceOld {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    private static final String URL_ATTRACTION_INFORMATION = "http://attraction-information/";
    private static final String URL_BIKE_RENTAL = "http://bike-rental/";
    private static final String URL_CAR_RENTAL = "http://car-rental/";
    private static final String URL_FLIGHT_RESERVATON = "http://flight-reservation/";
    private static final String URL_HOTEL_RESERVATON = "http://hotel-reservation/";


    //startWorkflow
    @GetMapping
    public String startWorkFlow() throws Exception {

        // Search for attractions
        searchAttractions();

        // Search for flights
        searchFlights();

        // Search for hotels
        searchHotels();

        // Rent a bike
        rentBike();

        // Rent a car
        rentCar();

        //finishWorkflow

        return "Sucesso";
    }

    private void rentCar() throws IOException, JsonParseException, JsonMappingException {
        String carsJson = restTemplate.getForObject(URL_CAR_RENTAL + "cars", String.class);
        List<Car> cars = mapper.readValue(carsJson, new TypeReference<List<Car>>() {
        });

        CarReservation carReservation = CarReservation.builder()
                .client(CarClient.builder().id(1L).name("Davi").build())
                .car(anyItem(cars))
                .date(LocalDate.now()).build();

        HttpEntity<CarReservation> carReservationRequest = new HttpEntity<>(carReservation);
        CarReservation carReservationResponse = restTemplate.postForObject(URL_CAR_RENTAL + "reservations", carReservationRequest, CarReservation.class);
        System.out.println(carReservationResponse);
    }

    private void rentBike() throws IOException, JsonParseException, JsonMappingException {
        String bikesJson = restTemplate.getForObject(URL_BIKE_RENTAL + "bikes", String.class);
        List<Bike> bikes = mapper.readValue(bikesJson, new TypeReference<List<Bike>>() {
        });

        BikeReservation bikeReservation = BikeReservation.builder()
                .client(BikeClient.builder().id(1L).name("Davi").build())
                .bike(anyItem(bikes))
                .date(LocalDate.now()).build();

        HttpEntity<BikeReservation> bikeReservationRequest = new HttpEntity<>(bikeReservation);
        BikeReservation bikeReservationResponse = restTemplate.postForObject(URL_BIKE_RENTAL + "reservations", bikeReservationRequest, BikeReservation.class);
        System.out.println(bikeReservationResponse);
    }

    private void searchHotels() throws IOException, JsonParseException, JsonMappingException {
        String hotelsJson = restTemplate.getForObject(URL_HOTEL_RESERVATON + "hotels", String.class);
        List<Hotel> hotels = mapper.readValue(hotelsJson, new TypeReference<List<Hotel>>() {
        });
        System.out.println(hotelsJson);
        hotels.forEach(System.out::println);
    }

    private void searchFlights() throws IOException, JsonParseException, JsonMappingException {
        String flightsJson = restTemplate.getForObject(URL_FLIGHT_RESERVATON + "flights", String.class);
        List<Flight> flights = mapper.readValue(flightsJson, new TypeReference<List<Flight>>() {
        });
        System.out.println(flightsJson);
        flights.forEach(System.out::println);
    }

    private void searchAttractions() throws IOException, JsonParseException, JsonMappingException {
        String attractionsJson = restTemplate.getForObject(URL_ATTRACTION_INFORMATION + "attractions", String.class);
        List<Attraction> attractions = mapper.readValue(attractionsJson, new TypeReference<List<Attraction>>() {
        });
        System.out.println(attractionsJson);
        attractions.forEach(System.out::println);

    }

    public <T> T anyItem(List<T> list) {
        Objects.requireNonNull(list);
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(list.size());
        return list.get(index);
    }

}

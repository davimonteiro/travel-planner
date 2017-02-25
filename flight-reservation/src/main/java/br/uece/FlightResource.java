package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/flights")
public class FlightResource {

	@GetMapping
	public List<Flight> searchFlights() {
		
		List<Flight> flights = Arrays.asList(Flight.builder().id(1L).descriton("A12673").build(),
				Flight.builder().id(2L).descriton("A12343").build(),
				Flight.builder().id(3L).descriton("A12233").build(),
				Flight.builder().id(4L).descriton("B14573").build(),
				Flight.builder().id(5L).descriton("C14573").build());

		return flights;
	}

}

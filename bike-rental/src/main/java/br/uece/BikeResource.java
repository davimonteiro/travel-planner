package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bikes")
public class BikeResource {

	@GetMapping
	public List<Bike> findAll() {
		List<Bike> flights = Arrays.asList(
				Bike.builder().id(1L).name("Bike 1").build(),
				Bike.builder().id(2L).name("Bike 2").build(),
				Bike.builder().id(3L).name("Bike 3").build(),
				Bike.builder().id(4L).name("Bike 4").build(),
				Bike.builder().id(5L).name("Bike 5").build());

		return flights;
	}
	
}

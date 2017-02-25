package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarResource {

	@GetMapping
	public List<Car> findAll() {
		List<Car> flights = Arrays.asList(Car.builder().id(1L).name("Car 1").build(),
				Car.builder().id(2L).name("Car 2").build(),
				Car.builder().id(3L).name("Car 3").build(),
				Car.builder().id(4L).name("Car 4").build(),
				Car.builder().id(5L).name("Car 5").build());

		return flights;
	}
	
}

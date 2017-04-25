package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/reservations")
public class CarReservationResource {
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<CarReservation> save(@RequestBody CarReservation reservation) {
		//save in database
		reservation.setId(1L);
		return ResponseEntity.ok(reservation);
	}
	
	@GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CarReservation>> findAll() {
		return ResponseEntity.ok(Arrays.asList(CarReservation.builder().build()));
	}

}

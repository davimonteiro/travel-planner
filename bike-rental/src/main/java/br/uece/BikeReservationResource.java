package br.uece;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/reservations")
public class BikeReservationResource {
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BikeReservation> save(@RequestBody BikeReservation reservation) {
		//save in database
		reservation.setId(1L);
		return ResponseEntity.ok(reservation);
	}
	
	@GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BikeReservation>> findAll() {
		return ResponseEntity.ok(Arrays.asList(BikeReservation.builder().build()));
	}

}

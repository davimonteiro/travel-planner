package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class BikeReservationResource {
	
	@PostMapping
	public ResponseEntity<BikeReservation> save(@RequestBody BikeReservation reservation) {
		//save in database
		reservation.setId(1L);
		return ResponseEntity.ok(reservation);
	}
	
	@GetMapping
	public ResponseEntity<List<BikeReservation>> findAll() {
		return ResponseEntity.ok(Arrays.asList(BikeReservation.builder().build()));
	}

}

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
public class ReservationResource {
	
	@PostMapping
	public ResponseEntity<Reservation> save(@RequestBody Reservation reservation) {
		//save in database
		return ResponseEntity.ok(reservation);
	}
	
	@GetMapping
	public ResponseEntity<List<Reservation>> findAll() {
		return ResponseEntity.ok(Arrays.asList(Reservation.builder().build()));
	}

}

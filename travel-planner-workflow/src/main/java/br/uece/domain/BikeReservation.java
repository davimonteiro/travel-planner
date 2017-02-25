package br.uece.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BikeReservation {

	private Long id;
	
	private LocalDate date;
	
	private Bike bike;
	
	private BikeClient client;
	
}

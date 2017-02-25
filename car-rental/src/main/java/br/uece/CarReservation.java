package br.uece;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CarReservation {

	private Long id;
	
	private LocalDate date;
	
	private Car car;
	
	private CarClient client;
	
}

package br.uece;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reservation {

	private Long id;
	
	private LocalDate date;
	
	private Car car;
	
	private Client client;
	
}

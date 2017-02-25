package br.uece;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
	
	private Long id;
	
	private String name;
	
}

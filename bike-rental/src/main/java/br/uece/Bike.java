package br.uece;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bike {
	
	private Long id;
	
	private String name;
	
}

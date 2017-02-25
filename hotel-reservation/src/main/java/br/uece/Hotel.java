package br.uece;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hotel {
	
	private Long id;
	
	private String name;
	
}

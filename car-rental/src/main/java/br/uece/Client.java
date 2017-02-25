package br.uece;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
	
	private Long id;
	
	private String name;
	
}

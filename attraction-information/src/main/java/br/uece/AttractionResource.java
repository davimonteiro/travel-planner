package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attractions")
public class AttractionResource {
	
	@GetMapping
	public ResponseEntity<List<Attraction>> searchAttractions() {
		
		List<Attraction> attractions = Arrays.asList(Attraction.builder().id(1L).name("Attraction 1").build(),
				Attraction.builder().id(2L).name("Attraction").build(),
				Attraction.builder().id(3L).name("Attraction 3").build(),
				Attraction.builder().id(4L).name("Attraction 4").build(),
				Attraction.builder().id(5L).name("Attraction 5").build());
		
		return ResponseEntity.ok(attractions);
	}

}

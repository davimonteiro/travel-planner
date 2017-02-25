package br.uece;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hotels")
public class HotelResource {

	@GetMapping
	public List<Hotel> searchHotels() {
		
		List<Hotel> hotels = Arrays.asList(Hotel.builder().id(1L).name("Hotel A").build(),
				Hotel.builder().id(2L).name("Hotel B").build(),
				Hotel.builder().id(3L).name("Hotel C").build(),
				Hotel.builder().id(4L).name("Hotel D").build(),
				Hotel.builder().id(5L).name("Hotel E").build());

		return hotels;
	}
	
	
}

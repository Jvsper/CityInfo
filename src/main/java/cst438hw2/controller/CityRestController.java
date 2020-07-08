package cst438hw2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;
import cst438hw2.service.WeatherService;

@RestController
public class CityRestController {
	
	@Autowired
	private CityService cityService;
	
	//@Autowired
	//private WeatherService weatherService;
	
	@GetMapping("/api/cities/{city}")
	public ResponseEntity getWeather(@PathVariable("city") String cityName) {
		
		
		return new ResponseEntity(cityService.getCityInfo(cityName),HttpStatus.OK);
		
	}
	
}

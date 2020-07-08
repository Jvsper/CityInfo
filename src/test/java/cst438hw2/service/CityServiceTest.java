package cst438hw2.service;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	// Class to obe tested
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	@BeforeEach
    public void setUpEach() {
		MockitoAnnotations.initMocks( this);
   	
   }
	
	@Test
	public void contextLoads() {
		
	}


	@Test
	public void testCityFound() throws Exception {
		
		String cityName = "Seattle";
		List<City> testCity = new ArrayList();
				testCity.add(new City(3816, cityName, "USA", "Washington", 563374));
		TempAndTime tempNtime = new TempAndTime(63.5, 1594160319 ,-25200);
		Country testCountry = new Country("USA", "United States");
		
		// Stubs
		given(cityRepository.findByName(cityName)).willReturn(testCity);
		given(weatherService.getTempAndTime(cityName)).willReturn(tempNtime);
		given(countryRepository.findByCode("USA")).willReturn(testCountry);
	
		cityService = new CityService(cityRepository, weatherService, countryRepository);
		CityInfo cityInfo = cityService.getCityInfo(cityName);
		
		// Converting to Fahrenheit
		double tempFahrenheit = (tempNtime.temp - 273.15) * 9.0/5.0 + 32.0;
		
		// Getting local time
		long timeZone = tempNtime.timezone * 1000;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		TimeZone tz = TimeZone.getTimeZone("UTC");
		tz.setRawOffset((int) timeZone);
		sdf.setTimeZone(tz);
		Date date = new Date(currentTime);
		String formattedDate  =  sdf.format(date);
	    
		//City city, String countryName, double temp, String time
		assertEquals(new CityInfo(testCity.get(0), testCountry.getName(), tempFahrenheit, formattedDate), cityInfo);

	}
	
	@Test 
	public void  testCityNotFound() {
		
		String cityName = "Seattle";
		List<City> testCity = new ArrayList <City>();
				testCity.add(new City(3816, cityName, "USA", "Washington", 563374));
		TempAndTime tempNtime = new TempAndTime(63.5, 1594160319 ,-25200);
		Country testCountry = new Country("USA", "United States");
		
		// Stubs
		given(cityRepository.findByName(cityName)).willReturn(testCity);
		given(weatherService.getTempAndTime(cityName)).willReturn(tempNtime);
		given(countryRepository.findByCode("USA")).willReturn(testCountry);
	
		cityService = new CityService(cityRepository, weatherService, countryRepository);
		CityInfo cityInfo = cityService.getCityInfo(cityName);
		
		// Converting to Fahrenheit
		double tempFahrenheit = (tempNtime.temp - 273.15) * 9.0/5.0 + 32.0;
		
		// Getting local time
		long timeZone = tempNtime.timezone * 1000;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		TimeZone tz = TimeZone.getTimeZone("UTC");
		tz.setRawOffset((int) timeZone);
		sdf.setTimeZone(tz);
		Date date = new Date(currentTime);
		String formattedDate  =  sdf.format(date);
	    
		//City city, String countryName, double temp, String time
		assertFalse(new CityInfo(testCity.get(0), "China", tempFahrenheit, formattedDate).equals(cityInfo));

	}
	
	@Test 
	public void  testCityMultiple() {
		
		String cityName = "Seattle";
		List<City> testCity = new ArrayList <City>();
				testCity.add(new City(3816, cityName, "USA", "Washington", 563374));
		TempAndTime tempNtime = new TempAndTime(63.5, 1594160319 ,-25200);
		Country testCountry = new Country("USA", "United States");
		
		// Stubs
		given(cityRepository.findByName(cityName)).willReturn(testCity);
		given(weatherService.getTempAndTime(cityName)).willReturn(tempNtime);
		given(countryRepository.findByCode("USA")).willReturn(testCountry);
	
		cityService = new CityService(cityRepository, weatherService, countryRepository);
		CityInfo cityInfo = cityService.getCityInfo(cityName);
		
		// Converting to Fahrenheit
		double tempFahrenheit = (tempNtime.temp - 273.15) * 9.0/5.0 + 32.0;
		
		// Getting local time
		long timeZone = tempNtime.timezone * 1000;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		TimeZone tz = TimeZone.getTimeZone("UTC");
		tz.setRawOffset((int) timeZone);
		sdf.setTimeZone(tz);
		Date date = new Date(currentTime);
		String formattedDate  =  sdf.format(date);
	    
		//City city, String countryName, double temp, String time
		assertFalse(new CityInfo(testCity.get(0), "China", tempFahrenheit, formattedDate).equals(cityInfo));
		
	}

}

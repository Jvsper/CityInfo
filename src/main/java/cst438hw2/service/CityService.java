package cst438hw2.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;

import cst438hw2.domain.*;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	// Constructor used to test
	public CityService(CityRepository cityRepository, WeatherService weatherService, CountryRepository countryRepository) {
		this.cityRepository = cityRepository;
		this.weatherService = weatherService;
		this.countryRepository = countryRepository;
		
	}
	
	public CityInfo getCityInfo(String cityName) {
		
		List<City> cities = cityRepository.findByName(cityName);
		CityInfo cityInfo;
		
		if (cities.size() == 1) {
			City city = cities.get(0);
			Country country = countryRepository.findByCode(city.getCountryCode());
			TempAndTime tempTime = weatherService.getTempAndTime(cityName);
			double tempFahrenheit = (tempTime.temp - 273.15) * 9.0/5.0 + 32.0;
			long timeZone = tempTime.timezone * 1000;
			long currentTime = System.currentTimeMillis();
		
			System.out.println("Current timezone offset : " + timeZone);
			System.out.println("Current time : " + currentTime);
			System.out.println("Current temp : " + tempFahrenheit);
			
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
			TimeZone tz = TimeZone.getTimeZone("UTC");
			tz.setRawOffset((int) timeZone);
			sdf.setTimeZone(tz);
			Date date = new Date(currentTime);
			String  formattedDate  =  sdf.format(date);
			
			// Setting double to hundred-th decimal place
			BigDecimal bd = new BigDecimal (tempFahrenheit).setScale(2, RoundingMode.HALF_UP);
			tempFahrenheit = bd.doubleValue();
			
			cityInfo = new CityInfo(city, country.getName(),tempFahrenheit , formattedDate);
			
			return cityInfo; 
		} else {
			
			return null;
		}
		 
	}
}


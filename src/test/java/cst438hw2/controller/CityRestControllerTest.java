package cst438hw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
		
		List<City> cities =new ArrayList<City>();
		CityInfo cityInfo = new CityInfo(1, "Seoul", "KR", "Korea","S", 3000, 200, "0:00 AM");
		given(cityService.getCityInfo("Seattle")).willReturn(cityInfo);
		
		given(cityRepository.findByName("Los angeles")).willReturn(cities);
		
		MockHttpServletResponse response = mvc.perform(get("/api/cities/Seattle")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		CityInfo cityResult = json.parseObject(response.getContentAsString());
		CityInfo expectedResult = new CityInfo(1, "Seoul", "KR", "Korea","S", 3000, 200, "0:00 AM");
		
		assertThat(cityResult).isEqualTo(expectedResult);
	}
	
	// assertFalse test
	@Test 
	public void canNotGetCityInfo() throws Exception {
		
		List<City> cities =new ArrayList<City>();
		CityInfo cityInfo = new CityInfo(1, "Seoul", "KR", "Korea","S", 3000, 200, "0:00 AM");
		given(cityService.getCityInfo("Seattle")).willReturn(cityInfo);
		
		given(cityRepository.findByName("Los angeles")).willReturn(cities);
		
		MockHttpServletResponse response = mvc.perform(get("/api/cities/Seattle")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		CityInfo cityResult = json.parseObject(response.getContentAsString());
		CityInfo expectedResult = new CityInfo(1, "Seoul", "KR", "Korea","S", 3000, 200, "0:00 AM");
		
		expectedResult.setTemp(30);
	
		assertFalse(cityResult.equals(expectedResult));
	}
	
	
}



package com.pernix;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pernix.controller.ServiceController;

import entities.Service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class ServiceControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	MockMvc mockMvc;

	Service service = new Service();

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void getTax() throws Exception {
		mockMvc.perform(get("/getServices").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=ISO-8859-1")).andDo(print());
	}

	@Test
	public void addCode() throws Exception {
		String codes = "01, ";
		String taxes = "01, ";
		service.setAmount("6");
		service.setComercialUnitOfMeasurement("Metro");
		service.setDetail("Metro de cuerda");
		service.setDiscount("0");
		service.setDiscountNature("No discount.");
		service.setLineNumber("14235");
		service.setPriceByUnit("1000");
		service.setSubTotal("6000");
		service.setTotalAmount("6000");
		service.setTotal("");
		service.setUnitOfMeasurementName("Metro");
		service.setUnitOfMeasurementType("m");
		
		mockMvc.perform(post("/addService").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content("amount="
						+ service.getAmount()
						+ "&codes="
						+ codes
						+ "&comercialUnitOfMeasurement="
						+ service.getComercialUnitOfMeasurement()
						+ "&detail=" 
						+ service.getDetail()
						+ "&discount=" 
						+ service.getDiscount()
						+ "&discountNature="
						+ service.getDiscountNature()
						+ "&lineNumber=" 
						+ service.getLineNumber() 
						+ "&priceByUnit=" 
						+ service.getPriceByUnit()
						+ "&subTotal=" 
						+ service.getSubTotal()
						+ "&totalAmount=" 
						+ service.getTotalAmount()
						+ "&unitOfMeasurementName=" 
						+ service.getUnitOfMeasurementName()
						+ "&unitOfMeasurementType=" 
						+ service.getUnitOfMeasurementType()
						+ "&taxes=" 
						+ taxes)).andExpect(status().isOk())
				.andDo(print());
	}

	@Configuration
	@EnableWebMvc
	public static class TestConfiguration {

		@Bean
		public ServiceController serviceController() {
			return new ServiceController();
		}

	}
}

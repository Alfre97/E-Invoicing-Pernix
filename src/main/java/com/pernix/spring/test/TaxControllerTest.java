package com.pernix.spring.test;

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

import com.pernix.spring.controller.TaxController;
import com.pernix.spring.model.Tax;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class TaxControllerTest {

    @Autowired
	private WebApplicationContext ctx;

	MockMvc mockMvc;

	Tax tax = new Tax();

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void getTax() throws Exception {
		mockMvc.perform(get("/getTaxes").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=ISO-8859-1")).andDo(print());
	}

	@Test
	public void addTax() throws Exception {
		
		tax.setCode("01");
		tax.setDate("4/17/2018, 8:33 AM");
		tax.setDocumentNumber("1010102");
		tax.setDocumentType("01");
		tax.setInstitutionName("ARESEP");
		tax.setPurchasePercentage("13");
		tax.setRate("0.13");
		
		mockMvc.perform(post("/addTax").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.content("code="
						+ tax.getCode()
						+ "&rate=" 
						+ tax.getRate()
						+ "&purchasePercentage="
						+ tax.getPurchasePercentage()
						+ "&date="
						+ tax.getDate()
						+ "&institutionName=" 
						+ tax.getInstitutionName()
						+ "&documentNumber="
						+ tax.getDocumentNumber()
						+ "&documentType=" 
						+ tax.getDocumentType())).andExpect(status().isOk())
				.andDo(print());
	}

	@Configuration
	@EnableWebMvc
	public static class TestConfiguration {

		@Bean
		public TaxController taxController() {
			return new TaxController();
		}

	}
}

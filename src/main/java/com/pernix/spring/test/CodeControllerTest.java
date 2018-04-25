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

import com.pernix.spring.controller.CodeController;
import com.pernix.spring.model.Code;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class CodeControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	MockMvc mockMvc;

	Code code = new Code();

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	
	 @Test public void getCode() throws Exception {
	 mockMvc.perform(get("/getCodes").accept(MediaType.APPLICATION_JSON))
	 .andExpect(status().isOk())
	 .andExpect(content().contentType("application/json;charset=ISO-8859-1"))
	 .andDo(print()); }
	 

	@Test
	public void addCode() throws Exception {
		Code code = new Code();
		code.setCodeType("01");
		code.setCode("12345678");
		mockMvc.perform(post("/addCode").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).content("codeType=" + code.getCodeType() 
																										+ "&code=" + code.getCode()))
				.andExpect(status().isOk()).andDo(print());
	}

	@Configuration
	@EnableWebMvc
	public static class TestConfiguration {

		@Bean
		public CodeController codeController() {
			return new CodeController();
		}

	}
}

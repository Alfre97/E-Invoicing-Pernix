import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pernix.einvoicing.controller.ServiceController;
import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.model.Services;
import com.pernix.einvoicing.model.Tax;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

@ContextConfiguration(classes = Application.class)
@RunWith(value = SpringRunner.class)
@WebMvcTest(ServiceController.class)
public class ServiceControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ServiceController serviceControllerMock;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void contexLoads() throws Exception {
		assertThat(serviceControllerMock).isNotNull();
	}

	public Services setUpService() {
		Services service = new Services();
		List<Code> codeList = new ArrayList<>();
		List<Tax> taxList = new ArrayList<>();
		codeList.add(new Code(new Long(1), "01", "12345", service));
		taxList.add(new Tax(new Long(1), "01", 650, 0.13, new Services(), "16/05/2018", 0, "", "", 13, ""));
		service = new Services(new Long(1), 1, codeList, 1, "m", "Metro", "Metro", "Un metro de tela roja", 5000.00, 1,
				0.0, "Sin descuento", 5000.00, taxList, 5000.00);
		return service;
	}

	@Test
	public void testAddService() throws Exception {
		Gson gson = new Gson();
		Services service = setUpService();
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

		given(serviceControllerMock.addService(service)).willReturn(response);

		mvc.perform(
				post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String(gson.toJson(service))))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testAddServiceEmpty() throws Exception {
		Gson gson = new Gson();
		Services service = new Services();
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

		given(serviceControllerMock.addService(service)).willReturn(response);

		mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(service)))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testAddServicePartial() throws Exception {
		Services service = null;
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

		given(serviceControllerMock.addService(service)).willReturn(response);

		mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(service))).andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void testAddServiceWrongContent() throws Exception {
		Services service = new Services();
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

		given(serviceControllerMock.addService(service)).willReturn(response);

		mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String("1")))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void testGetServices() throws Exception {
		mvc.perform(get("/getServices")).andDo(print())
				.andExpect(status().isOk()).andExpect(model().attribute("jsonService", String.class)).andReturn();
	}

	@Test
	public void testDeleteService() throws Exception {
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

		given(serviceControllerMock.deleteService(new Long(1))).willReturn(response);

		mvc.perform(post("/deleteService").param("id", "1")).andExpect(status().isOk()).andDo(print());
	}
}

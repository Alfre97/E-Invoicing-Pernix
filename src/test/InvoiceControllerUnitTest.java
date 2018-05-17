import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.pernix.einvoicing.controller.InvoiceController;
import com.pernix.einvoicing.hacienda.jaxb.FacturaElectronica;
import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.model.Emitter;
import com.pernix.einvoicing.model.Invoice;
import com.pernix.einvoicing.model.Receiver;
import com.pernix.einvoicing.model.Services;
import com.pernix.einvoicing.model.Tax;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

@ContextConfiguration(classes = Application.class)
@RunWith(value = SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private InvoiceController invoiceControllerMock;

	@Autowired
	ObjectMapper objectMapper;

		@Test
		public void contexLoads() throws Exception {
			assertThat(invoiceControllerMock).isNotNull();
		}

		public Invoice setUpInvoice() {
			Services service = new Services();
			Invoice invoice = new Invoice();
			Emitter emitter = new Emitter();
			Receiver receiver = new Receiver();
			List<Code> codeList = new ArrayList<>();
			List<Tax> taxList = new ArrayList<>();
			List<Services> serviceList = new ArrayList<>();
			
			codeList.add(new Code(new Long("1"), "01", "12345", service));
			taxList.add(new Tax(new Long("1"), "01", 650, 0.13, new Services(), "16/05/2018", 0, "", "", 13, ""));
			service = new Services(new Long("1"), 1, codeList, 1, "m", "Metro", "Metro", "Un metro de tela roja", 5000.00, 1, 0.0, "Sin descuento", 5000.00, taxList ,5000.00);
			serviceList.add(service);
			invoice = new Invoice(new Long("1"), "", "", "2018/01/01 00:02:00", emitter, receiver, "Contado", "", "Efectivo", "CRC",
					0.0, 0.0, 0.0, 5000.00, 0.0, 5000.00, 0.0, 5000.00, 0.0, 5000.00, 650.00, 5650.00, "DGT-R-48-2016", "20-02-2017 13:22:22", "", serviceList, "");
			return invoice;
		}

		@Test
		public void testInvoiceConstruction() throws Exception {
			Gson gson = new Gson();
			ResponseEntity<FacturaElectronica> response = new ResponseEntity<FacturaElectronica>(Mockito.any(FacturaElectronica.class), HttpStatus.OK);

			given(invoiceControllerMock.sendInvoice(setUpInvoice())).willReturn(response);

			mvc.perform(
					post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String(gson.toJson(setUpInvoice()))))
					.andExpect(status().isOk()).andDo(print());
		}
		
		@Test
		public void testInvoiceConstructionEmpty() throws Exception {
			Gson gson = new Gson();
			Invoice invoice = new Invoice();
			ResponseEntity<FacturaElectronica> response = new ResponseEntity<FacturaElectronica>(Mockito.any(FacturaElectronica.class), HttpStatus.OK);

			given(invoiceControllerMock.sendInvoice(setUpInvoice())).willReturn(response);

			mvc.perform(
					post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String(gson.toJson(invoice))));
		}
		
		@Test
		public void testAddInvoice() throws Exception {
			Gson gson = new Gson();
			Invoice invoice = setUpInvoice();
			ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

			given(invoiceControllerMock.addInvoice(invoice)).willReturn(response);

			mvc.perform(
					post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String(gson.toJson(invoice))))
					.andExpect(status().isOk()).andDo(print());
		}

		@Test
		public void testAddInvoiceEmpty() throws Exception {
			Gson gson = new Gson();
			Invoice invoice = setUpInvoice();
			ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

			given(invoiceControllerMock.addInvoice(invoice)).willReturn(response);

			mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(invoice)))
					.andExpect(status().isOk()).andDo(print());
		}

		@Test
		public void testAddInvoicePartial() throws Exception {
			Invoice invoice = null;
			ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

			given(invoiceControllerMock.addInvoice(invoice)).willReturn(response);

			mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(invoice))).andExpect(status().isBadRequest()).andDo(print());
		}

		@Test
		public void testAddInvoiceWrongContent() throws Exception {
			Invoice invoice = new Invoice();
			ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

			given(invoiceControllerMock.addInvoice(invoice)).willReturn(response);

			mvc.perform(post("/addService").contentType(MediaType.APPLICATION_JSON).content(new String("1")))
					.andExpect(status().isBadRequest()).andDo(print());
		}

		@Test
		public void testGetInvoices() throws Exception {
			mvc.perform(get("/getServices")).andDo(print())
					.andExpect(status().isOk()).andExpect(model().attribute("jsonService", String.class)).andReturn();
		}

		@Test
		public void testDeleteInvoice() throws Exception {
			ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);

			given(invoiceControllerMock.deleteInvoice(new Long(1))).willReturn(response);

			mvc.perform(post("/deleteService").param("id", "1")).andExpect(status().isOk()).andDo(print());
		}
}

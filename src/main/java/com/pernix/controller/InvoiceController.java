package com.pernix.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.emitter.EmitterException;

import com.pernix.hacienda.jaxb.EmisorType;
import com.pernix.hacienda.jaxb.FacturaElectronica;
import com.pernix.hacienda.jaxb.TelefonoType;
import com.pernix.service.hacienda.invoicing.InvoicerService;

import entities.Invoice;
import entities.UserEmitterReceiver;
import services.UserService;

@RestController
@RequestMapping("api/v1")
public class InvoiceController {

	@Autowired
	InvoicerService HaciendaInvoicer;

	@RequestMapping(value = "/uploadInvoice", method = RequestMethod.POST)
	public Response uploadInvoice(String key, String consecutiveNumber, String dateCreated, String sellTerm,
			String paymentLapse, String paymentMethod, String selectedCurrency, String exchangeRate,
			String recordedServices, String exemptServices, String recordedCommodity, String exemptCommodity,
			String recordedTotal, String exemptTotal, String totalSell, String totalDiscount, String netSell,
			String totalTax, String totalVoucher, String resolutionNumber, String resolutionDate, String otherText,
			int idEmitter, int idReceiver, int idService)
			throws IllegalArgumentException, InvocationTargetException, Exception {

		key = generateInvoiceKey(dateCreated, idEmitter, consecutiveNumber);
		System.out.println(key);
		
		consecutiveNumber = generateConsecutive(consecutiveNumber);
		System.out.println(key);
		
		/*FacturaElectronica facturaElectronica = new FacturaElectronica();
		facturaElectronica.setClave(key);
		facturaElectronica.setNumeroConsecutivo(consecutiveNumber);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(dateCreated);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

		facturaElectronica.setFechaEmision(xmlGregCal);
		UserService userService = new UserService();
		UserEmitterReceiver emitter = new UserEmitterReceiver();
		emitter.setId(idEmitter);
		emitter = userService.read(emitter);
		EmisorType emitterType = new EmisorType();
		emitterType.setCorreoElectronico(emitter.getEmail());
		TelefonoType fax = new TelefonoType();
		BigInteger countryFax = new BigInteger(emitter.getFaxCountryCode());
		BigInteger numberFax = new BigInteger(emitter.getFaxNumber());
		fax.setCodigoPais(countryFax);
		fax.setNumTelefono(numberFax);
		JAXBElement<TelefonoType> JAXBFax = new JAXBElement<TelefonoType>(null, null, null, fax);
		emitterType.setFax(JAXBFax);

		/*
		 * String locationValue = HaciendaInvoicer.save(invoice);
		 * //if(StringUtils.isEmpty(locationValue)) //return
		 * Response.status(400).build();
		 */
		JsonObject jsonResponse = Json.createObjectBuilder().add("message", "Invoice under validation").build();
		return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
	}

	private String generateInvoiceKey(String date, int idEmitter, String consecutiveNumber) {
		// Country
		String key = "";
		key += "506";
		// Date
		String[] dateAndHour = date.split(" ");
		String[] dates = dateAndHour[0].split("-");
		String year = dates[0];
		String month = dates[1];
		String day = dates[2];
		year = splitYear(year);

		key += day;
		key += month;
		key += year;
		
		UserEmitterReceiver user = getUser(idEmitter);
		key += user.getIdentificationNumber();
		
		key += generateConsecutive(consecutiveNumber);
		
		//This is the state of the invoice
		key += "1";
		
		//This have to be a security code generated by us 
		key += "00001111";
		return key;
	}

	private String splitYear(String year) {
		year = year.substring(0, year.length() - 2);
		return year;
	}

	private UserEmitterReceiver getUser(int idEmitter) {
		try {
			UserEmitterReceiver user = new UserEmitterReceiver();
			UserService userService = new UserService();
			user.setId(idEmitter);
			user = userService.read(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public String generateConsecutive(String consecutive) {
		// Place where invoice is generated
		consecutive += "001";
		// Terminal or sell point where invoice is emitted
		consecutive += "0001";
		// King of electronic document, for invoice is 01
		consecutive += "01";
		// Consecutive for electronic invoice or associated document
		consecutive += "0000000001";

		// This data have to be storage in our DB
		return consecutive;
	}
}

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
    @RequestMapping(value="/uploadInvoice", method = RequestMethod.POST)
    public Response uploadInvoice(String key, String consecutiveNumber, String dateCreated, String sellTerm, String paymentLapse, String paymentMethod, String selectedCurrency,
    		String exchangeRate, String recordedServices, String exemptServices, String recordedCommodity, String exemptCommodity, String recordedTotal, String exemptTotal,
    		String totalSell, String totalDiscount, String netSell, String totalTax, String totalVoucher, String resolutionNumber, String resolutionDate, String otherText, int idEmitter,
    		int idReceiver, int idService) throws IllegalArgumentException, InvocationTargetException, Exception{  	
    		FacturaElectronica facturaElectronica= new FacturaElectronica();
    		facturaElectronica.setClave(key);
    		facturaElectronica.setNumeroConsecutivo(consecutiveNumber);
    		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		Date date = format.parse(dateCreated);
    		GregorianCalendar cal = new GregorianCalendar();
    		cal.setTime(date);
    		XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

    		facturaElectronica.setFechaEmision(xmlGregCal);
    		UserService userService= new UserService();
    		UserEmitterReceiver emitter= new UserEmitterReceiver();
    		emitter.setId(idEmitter);
    		emitter= userService.read(emitter);
    		EmisorType emitterType=new EmisorType();
    		emitterType.setCorreoElectronico(emitter.getEmail());
    		TelefonoType fax = new TelefonoType();
    		BigInteger countryFax= new BigInteger(emitter.getFaxCountryCode()); 
    		BigInteger numberFax= new BigInteger(emitter.getFaxNumber());
    		fax.setCodigoPais(countryFax);
    		fax.setNumTelefono(numberFax);
    		JAXBElement<TelefonoType> JAXBFax= new JAXBElement<TelefonoType>(null, null, null, fax);
    		emitterType.setFax(JAXBFax);

    		
    	/*
    	 * key:String, consecutiveNumber: String, dateCreated: String, sellTerm: String, paymentLapse:String, paymentMethod: String,selectedCurrency: String, exchangeRate: String,
    recordedServices: String, exemptServices: String, recordedCommodity: String, exemptCommodity: String, recordedTotal:String, exemptTotal:String, totalSell:String,
    totalDiscount:String, netSell:String, totalTax:String, totalVoucher:String, resolutionNumber:String, resolutionDate:String, otherText:String 
    	 
    		String locationValue = HaciendaInvoicer.save(invoice);
        //if(StringUtils.isEmpty(locationValue))
            //return Response.status(400).build();*/
        JsonObject jsonResponse = Json.createObjectBuilder().add("message", "Invoice under validation").build();
        return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
    }
}

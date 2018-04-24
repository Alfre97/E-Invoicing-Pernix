package com.pernix.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pernix.hacienda.jaxb.CodigoType;
import com.pernix.hacienda.jaxb.EmisorType;
import com.pernix.hacienda.jaxb.ExoneracionType;
import com.pernix.hacienda.jaxb.FacturaElectronica;
import com.pernix.hacienda.jaxb.IdentificacionType;
import com.pernix.hacienda.jaxb.ImpuestoType;
import com.pernix.hacienda.jaxb.ReceptorType;
import com.pernix.hacienda.jaxb.TelefonoType;
import com.pernix.hacienda.jaxb.UbicacionType;
import com.pernix.service.database.ServiceService;
import com.pernix.service.database.UserService;
import com.pernix.entities.Code;
import com.pernix.entities.Service;
import com.pernix.entities.Tax;
import com.pernix.entities.UserEmitterReceiver;

@RestController
public class InvoiceController {

	/*
	 * @Autowired InvoicerService HaciendaInvoicer;
	 */

	@RequestMapping("/uploadInvoice")
	public Response uploadInvoice(
			@RequestParam String dateCreated, 
			@RequestParam String sellTerm,
			@RequestParam String paymentLapse, 
			@RequestParam String paymentMethod,
			@RequestParam String selectedCurrency,
			@RequestParam String exchangeRate,
			@RequestParam String recordedServices,
			@RequestParam String exemptServices,
			@RequestParam String recordedCommodity, 
			@RequestParam String exemptCommodity,
			@RequestParam String recordedTotal, 
			@RequestParam String exemptTotal, 
			@RequestParam String totalSell,
			@RequestParam String totalDiscount, 
			@RequestParam String netSell, 
			@RequestParam String totalTax,
			@RequestParam String totalVoucher, 
			@RequestParam String resolutionNumber,
			@RequestParam String resolutionDate,
			@RequestParam String otherText, 
			@RequestParam int idEmitter,
			@RequestParam int idReceiver, 
			@RequestParam String servicesIds, 
			@RequestParam String referenceInfo, 
			@RequestParam String others)
			throws IllegalArgumentException, InvocationTargetException, Exception {

		String consecutiveNumber = "";
		consecutiveNumber = generateConsecutive();
		String key = generateInvoiceKey(dateCreated, idEmitter, consecutiveNumber);

		FacturaElectronica facturaElectronica = new FacturaElectronica();
		facturaElectronica.setClave(key);
		facturaElectronica.setNumeroConsecutivo(consecutiveNumber);
		facturaElectronica.setFechaEmision(constructDate(dateCreated));
		facturaElectronica.setEmisor(constructEmitter(idEmitter));
		facturaElectronica.setReceptor(constructReceiver(idReceiver));
		facturaElectronica.setCondicionVenta(sellTerm);
		facturaElectronica.setPlazoCredito(paymentLapse);
		facturaElectronica.getMedioPago().add(paymentMethod);
		facturaElectronica.setDetalleServicio(constructServiceDetail(servicesIds));
		facturaElectronica.setResumenFactura(constructInvoiceResume(selectedCurrency, exchangeRate, recordedServices, exemptServices, recordedCommodity, exemptCommodity, recordedTotal, exemptTotal, totalSell, totalDiscount, netSell, totalTax, totalVoucher));
		facturaElectronica = constructReferenceInfo(referenceInfo, facturaElectronica);
		facturaElectronica.getNormativa().setFechaResolucion(resolutionDate);
		facturaElectronica.getNormativa().setNumeroResolucion(resolutionNumber);
		FacturaElectronica.Otros.OtroTexto otroTexto = new FacturaElectronica.Otros.OtroTexto();
		otroTexto.setValue(others);
		facturaElectronica.getOtros().getOtroTexto().add(otroTexto);
		
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("message", "Invoice under validation").build();
		return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
	}
	
	private FacturaElectronica constructReferenceInfo(String referenceInfo, FacturaElectronica factura) throws Exception {
		String[] referenceList = referenceInfo.split(", ");
		FacturaElectronica.InformacionReferencia reference = new FacturaElectronica.InformacionReferencia();
		for (int i = 0; i < referenceList.length; i++) {
			String[] referenceInfoSplited = referenceList[i].split("-");
			if(referenceInfoSplited.length == 5) {
				reference.setTipoDoc(referenceInfoSplited[0]);
				reference.setNumero(referenceInfoSplited[1]);
				reference.setFechaEmision(constructDate(referenceInfoSplited[2]));
				reference.setCodigo(referenceInfoSplited[3]);
				reference.setRazon(referenceInfoSplited[4]);
			} else {
				reference = null;
			}
			factura.getInformacionReferencia().add(reference);			
		}
		return factura;
	}
	
	private FacturaElectronica.ResumenFactura constructInvoiceResume(
			String selectedCurrency,
			String exchangeRate,
			String recordedServices,
			String exemptServices,
			String recordedCommodity,
			String exemptCommodity,
			String recordedTotal,
			String exemptTotal,
			String totalSell,
			String totalDiscount,
			String netSell,
			String totalTax,
			String totalVoucher) {
		
		FacturaElectronica.ResumenFactura invoiceResume = new FacturaElectronica.ResumenFactura();
		invoiceResume.setCodigoMoneda(selectedCurrency);
		invoiceResume.setTipoCambio(new BigDecimal(exchangeRate));
		invoiceResume.setTotalServGravados(new BigDecimal(recordedServices));
		invoiceResume.setTotalServExentos(new BigDecimal(exemptServices));
		invoiceResume.setTotalMercanciasGravadas(new BigDecimal(recordedCommodity));
		invoiceResume.setTotalMercanciasExentas(new BigDecimal(exemptCommodity));
		invoiceResume.setTotalGravado(new BigDecimal(recordedTotal));
		invoiceResume.setTotalExento(new BigDecimal(exemptTotal));
		invoiceResume.setTotalVenta(new BigDecimal(totalSell));
		invoiceResume.setTotalDescuentos(new BigDecimal(totalDiscount));
		invoiceResume.setTotalVentaNeta(new BigDecimal(netSell));
		invoiceResume.setTotalImpuesto(new BigDecimal(totalTax));
		invoiceResume.setTotalComprobante(new BigDecimal(totalVoucher));
		return invoiceResume;
	}

	private FacturaElectronica.DetalleServicio constructServiceDetail(String servicesIds)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();

		String[] servicesIdsList = servicesIds.split(", ");
		ArrayList<Service> servicesList = constructServicesList(servicesIdsList);
		for (Service serviceTemp : servicesList) {
			detalleServicio.getLineaDetalle().add(constructDetailLine(serviceTemp));
		}
		return detalleServicio;
	}

	private FacturaElectronica.DetalleServicio.LineaDetalle constructDetailLine(Service serviceTemp) throws Exception { 
		CodigoType code = new CodigoType();
		FacturaElectronica.DetalleServicio.LineaDetalle lineaDetalle = new FacturaElectronica.DetalleServicio.LineaDetalle();
		lineaDetalle.setNumeroLinea(new BigInteger(serviceTemp.getLineNumber()));
		
		for (Code codeTemp : serviceTemp.getCodeList()) {
			code.setTipo(codeTemp.getCodeType());
			code.setCodigo(codeTemp.getCode());
			lineaDetalle.getCodigo().add(code);
			code = new CodigoType();
		}	
		
		lineaDetalle.setCantidad(new BigDecimal(serviceTemp.getAmount()));
		lineaDetalle.setUnidadMedida(serviceTemp.getUnitOfMeasurementType());
		lineaDetalle.setUnidadMedidaComercial(serviceTemp.getComercialUnitOfMeasurement());
		lineaDetalle.setDetalle(serviceTemp.getDetail());
		lineaDetalle.setPrecioUnitario(new BigDecimal(serviceTemp.getPriceByUnit()));
		lineaDetalle.setMontoTotal(new BigDecimal(serviceTemp.getTotalAmount())); 
		lineaDetalle.setMontoDescuento(new BigDecimal(serviceTemp.getDiscount()));
		lineaDetalle.setNaturalezaDescuento(serviceTemp.getDiscountNature());
		lineaDetalle.setMontoTotal(new BigDecimal(serviceTemp.getSubTotal()));
		
		for (Tax tax : serviceTemp.getTaxList()) {
			lineaDetalle.getImpuesto().add(constructTax(tax));
		}
		
		lineaDetalle.setMontoTotal(new BigDecimal(serviceTemp.getTotal()));
		return lineaDetalle;
	}
	
	private ImpuestoType constructTax(Tax tax) throws Exception {
		ImpuestoType taxType = new ImpuestoType();
		taxType.setCodigo(tax.getCode());
		taxType.setExoneracion(constructExoneration(tax));
		taxType.setMonto(new BigDecimal(tax.getTaxTotal()));
		taxType.setTarifa(new BigDecimal(tax.getRate()));
		return taxType;
	}
	
	private ExoneracionType constructExoneration(Tax tax) throws Exception {
		ExoneracionType exonerationType = new ExoneracionType();
		exonerationType.setFechaEmision(constructDate(tax.getDate()));
		exonerationType.setMontoImpuesto(new BigDecimal(tax.getTaxExonarated()));
		exonerationType.setNombreInstitucion(tax.getInstitutionName());
		exonerationType.setNumeroDocumento(tax.getDocumentNumber());
		exonerationType.setPorcentajeCompra(new BigInteger(tax.getPurchasePercentage()));
		exonerationType.setTipoDocumento(tax.getDocumentType());
		return exonerationType;
	}

	private ArrayList<Service> constructServicesList(String[] servicesIdsList)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		ArrayList<Service> servicesList = new ArrayList<Service>();
		ServiceService serviceService = new ServiceService();
		Service service = new Service();

		for (int i = 0; i < servicesIdsList.length; i++) {
			service.setId(Integer.parseInt(servicesIdsList[i]));
			service = serviceService.read(service);
			servicesList.add(service);
			service = new Service();
		}
		return servicesList;
	}

	private XMLGregorianCalendar constructDate(String dateCreated) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(dateCreated);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		return xmlGregCal;
	}

	private EmisorType constructEmitter(int idEmitter) throws Exception {
		UserService userService = new UserService();
		UserEmitterReceiver emitter = new UserEmitterReceiver();
		emitter.setId(idEmitter);
		emitter = userService.read(emitter);
		EmisorType emitterType = new EmisorType();
		emitterType.setNombre(emitter.getName());

		IdentificacionType identification = new IdentificacionType();
		identification.setTipo(emitter.getIdentificationType());
		identification.setNumero(emitter.getIdentificationNumber());
		emitterType.setIdentificacion(identification);

		emitterType.setNombreComercial(emitter.getComercialName());

		UbicacionType ubication = new UbicacionType();
		ubication.setProvincia(emitter.getLocationProvinceName());
		ubication.setCanton(emitter.getLocationCantonName());
		ubication.setDistrito(emitter.getLocationDistrictName());
		ubication.setBarrio(emitter.getLocationNeighborhoodName());
		ubication.setOtrasSenas(emitter.getOtherSignals());

		emitterType.setUbicacion(ubication);
		emitterType.setCorreoElectronico(emitter.getEmail());

		TelefonoType phone = new TelefonoType();
		BigInteger countryPhoneCode = new BigInteger(emitter.getPhoneCountryCode());
		BigInteger phoneNumber = new BigInteger(emitter.getPhoneNumber());
		phone.setCodigoPais(countryPhoneCode);
		phone.setNumTelefono(phoneNumber);

		TelefonoType fax = new TelefonoType();
		BigInteger countryFaxCode = new BigInteger(emitter.getFaxCountryCode());
		BigInteger faxNumber = new BigInteger(emitter.getFaxNumber());
		fax.setCodigoPais(countryFaxCode);
		fax.setNumTelefono(faxNumber);

		JAXBElement<TelefonoType> JAXBPhone = new JAXBElement<TelefonoType>(null, null, null, phone);
		JAXBElement<TelefonoType> JAXBFax = new JAXBElement<TelefonoType>(null, null, null, fax);
		emitterType.setTelefono(JAXBPhone);
		emitterType.setFax(JAXBFax);
		emitterType.setCorreoElectronico(emitter.getEmail());

		return emitterType;
	}

	private ReceptorType constructReceiver(int idReceiver) throws Exception {
		UserService userService = new UserService();
		UserEmitterReceiver receiver = new UserEmitterReceiver();
		receiver.setId(idReceiver);
		receiver = userService.read(receiver);
		ReceptorType receiverType = new ReceptorType();
		receiverType.setNombre(receiver.getName());

		IdentificacionType identification = new IdentificacionType();
		identification.setTipo(receiver.getIdentificationType());
		identification.setNumero(receiver.getIdentificationNumber());
		receiverType.setIdentificacion(identification);

		receiverType.setNombreComercial(receiver.getComercialName());

		UbicacionType ubication = new UbicacionType();
		ubication.setProvincia(receiver.getLocationProvinceName());
		ubication.setCanton(receiver.getLocationCantonName());
		ubication.setDistrito(receiver.getLocationDistrictName());
		ubication.setBarrio(receiver.getLocationNeighborhoodName());
		ubication.setOtrasSenas(receiver.getOtherSignals());

		receiverType.setUbicacion(ubication);
		receiverType.setCorreoElectronico(receiver.getEmail());

		TelefonoType phone = new TelefonoType();
		BigInteger countryPhoneCode = new BigInteger(receiver.getPhoneCountryCode());
		BigInteger phoneNumber = new BigInteger(receiver.getPhoneNumber());
		phone.setCodigoPais(countryPhoneCode);
		phone.setNumTelefono(phoneNumber);

		TelefonoType fax = new TelefonoType();
		BigInteger countryFaxCode = new BigInteger(receiver.getFaxCountryCode());
		BigInteger faxNumber = new BigInteger(receiver.getFaxNumber());
		fax.setCodigoPais(countryFaxCode);
		fax.setNumTelefono(faxNumber);

		receiverType.setTelefono(phone);
		receiverType.setFax(fax);
		receiverType.setCorreoElectronico(receiver.getEmail());

		return receiverType;
	}

	private String generateInvoiceKey(String date, int idEmitter, String consecutiveNumber) {
		try {
			// Country
			String key = "";
			key += "506";
			// Date
			String[] dateAndHour = date.split(" ");
			String[] dates = dateAndHour[0].split("/");
			String day = dates[0];
			String month = dates[1];
			String year = dates[2];
			year = divideYear(year);

			key += day;
			key += month;
			key += year;

			UserEmitterReceiver user = getUser(idEmitter);
			key += user.getIdentificationNumber();

			key += generateConsecutive();

			// This is the state of the invoice
			key += "1";

			// This have to be a security code generated by us
			key += "00001111";
			return key;
		} catch (Exception e) {
			return null;
		}
	}

	private String divideYear(String year) {
		try {
			year = year.substring(0, year.length() - 1);
			year = year.substring(2, year.length());
		} catch (Exception e) {
			return null;
		}
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

	public String generateConsecutive() {
		String consecutive = "";
		// Place where invoice is generated
		consecutive += "001";
		// Terminal or sell point where invoice is emitted
		consecutive += "0001";
		// Key of electronic document, for invoice is 01
		consecutive += "01";
		// Consecutive for electronic invoice or associated document
		consecutive += "0000000001";

		// This data have to be storage in our DB
		return consecutive;
	}
}

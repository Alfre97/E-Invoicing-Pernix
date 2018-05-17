package com.pernix.einvoicing.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pernix.einvoicing.hacienda.jaxb.CodigoType;
import com.pernix.einvoicing.hacienda.jaxb.EmisorType;
import com.pernix.einvoicing.hacienda.jaxb.ExoneracionType;
import com.pernix.einvoicing.hacienda.jaxb.FacturaElectronica;
import com.pernix.einvoicing.hacienda.jaxb.IdentificacionType;
import com.pernix.einvoicing.hacienda.jaxb.ImpuestoType;
import com.pernix.einvoicing.hacienda.jaxb.ReceptorType;
import com.pernix.einvoicing.hacienda.jaxb.TelefonoType;
import com.pernix.einvoicing.hacienda.jaxb.UbicacionType;
import com.pernix.einvoicing.model.Code;
import com.pernix.einvoicing.model.Emitter;
import com.pernix.einvoicing.model.Invoice;
import com.pernix.einvoicing.model.Services;
import com.pernix.einvoicing.model.Tax;
import com.pernix.einvoicing.model.Receiver;
import com.pernix.einvoicing.service.EmitterService;
import com.pernix.einvoicing.service.InvoiceService;
import com.pernix.einvoicing.service.ReceiverService;

@RestController
public class InvoiceController {

	/*
	 * @Autowired InvoicerService HaciendaInvoicer;
	 */

	@Autowired
	private InvoiceService invoiceService;

	@RequestMapping("/addInvoice")
	public ResponseEntity<Boolean> addInvoice(@RequestBody Invoice invoice)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		String consecutiveNumber = "";
		consecutiveNumber = generateConsecutive();
		String key = generateInvoiceKey(invoice.getDate(), invoice.getEmitter().getId(), consecutiveNumber);
		invoice.setConsecutive(consecutiveNumber);
		invoice.setKey(key);
		invoiceService.addInvoice(invoice);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@RequestMapping("/sendInvoice")
	public ResponseEntity<FacturaElectronica> sendInvoice(@RequestBody Invoice invoice)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		try {
			FacturaElectronica facturaElectronica = new FacturaElectronica();
			facturaElectronica.setClave(invoice.getKey());
			facturaElectronica.setNumeroConsecutivo(invoice.getConsecutive());
			facturaElectronica.setFechaEmision(constructDate(invoice.getDate()));
			facturaElectronica.setEmisor(constructEmitter(invoice.getEmitter().getId()));
			facturaElectronica.setReceptor(constructReceiver(invoice.getReceiver().getId()));
			facturaElectronica.setCondicionVenta(invoice.getSellCondition());
			facturaElectronica.setPlazoCredito(invoice.getPaymentLapse());
			facturaElectronica.getMedioPago().add(invoice.getPaymentLapse());
			facturaElectronica.setDetalleServicio(constructServiceDetail(invoice.getServiceList()));
			facturaElectronica.setResumenFactura(constructInvoiceResume(invoice.getCurrency(),
					invoice.getExchangeRate(), invoice.getRecordedServices(), invoice.getExemptServices(),
					invoice.getRecordedCommodity(), invoice.getExemptCommodity(), invoice.getRecordedTotal(),
					invoice.getExemptTotal(), invoice.getTotalSell(), invoice.getTotalDiscount(), invoice.getNetSell(),
					invoice.getTotalTax(), invoice.getTotalVoucher()));
			facturaElectronica.getNormativa().setFechaResolucion(invoice.getResolutionDate());
			facturaElectronica.getNormativa().setNumeroResolucion(invoice.getResolutionNumber());
			FacturaElectronica.Otros.OtroTexto otroTexto = new FacturaElectronica.Otros.OtroTexto();
			otroTexto.setValue(invoice.getOtherText());
			facturaElectronica.getOtros().getOtroTexto().add(otroTexto);
			return new ResponseEntity<FacturaElectronica>(facturaElectronica, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<FacturaElectronica>(HttpStatus.CONFLICT);
		}
	}

	private FacturaElectronica.ResumenFactura constructInvoiceResume(String selectedCurrency, double exchangeRate,
			double recordedServices, double exemptServices, double recordedCommodity, double exemptCommodity,
			double recordedTotal, double exemptTotal, double totalSell, double totalDiscount, double netSell,
			double totalTax, double totalVoucher) {
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

	private FacturaElectronica.DetalleServicio constructServiceDetail(List<Services> servicesList)
			throws IllegalArgumentException, InvocationTargetException, Exception {
		FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();
		for (Services serviceTemp : servicesList) {
			detalleServicio.getLineaDetalle().add(constructDetailLine(serviceTemp));
		}
		return detalleServicio;
	}

	private FacturaElectronica.DetalleServicio.LineaDetalle constructDetailLine(Services serviceTemp) throws Exception {
		CodigoType codeType = new CodigoType();
		FacturaElectronica.DetalleServicio.LineaDetalle lineaDetalle = new FacturaElectronica.DetalleServicio.LineaDetalle();
		lineaDetalle.setNumeroLinea(new BigInteger(Integer.toString(serviceTemp.getLineNumber())));

		for (Code codeTemp : serviceTemp.getCodeList()) {
			codeType.setTipo(codeTemp.getCodeType());
			codeType.setCodigo(codeTemp.getCode());
			lineaDetalle.getCodigo().add(codeType);
			codeType = new CodigoType();
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
		exonerationType.setPorcentajeCompra(new BigInteger(Integer.toString(tax.getPurchasePercentage())));
		exonerationType.setTipoDocumento(tax.getDocumentType());
		return exonerationType;
	}

	private XMLGregorianCalendar constructDate(String dateCreated) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(dateCreated);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		return xmlGregCal;
	}

	private EmisorType constructEmitter(Long idEmitter) throws Exception {
		EmitterService userService = new EmitterService();
		Emitter emitter = new Emitter();
		emitter.setId(idEmitter);
		emitter = userService.findOneEmitter(emitter);
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

	private ReceptorType constructReceiver(Long idReceiver) throws Exception {
		ReceiverService userService = new ReceiverService();
		Receiver receiver = new Receiver();
		receiver.setId(idReceiver);
		receiver = userService.findOneReceiver(receiver);
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

	private String generateInvoiceKey(String date, Long idEmitter, String consecutiveNumber) {
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

			Emitter emitter = getUser(idEmitter);
			key += emitter.getIdentificationNumber();

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

	private Emitter getUser(Long idEmitter) {
		try {
			Emitter emitter = new Emitter();
			EmitterService emitterService = new EmitterService();
			emitter.setId(idEmitter);
			emitter = emitterService.findOneEmitter(emitter);
			return emitter;
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

	@RequestMapping("/getInvoices")
	public ResponseEntity<String> getInvoices() throws Exception {
		Gson gson = new Gson();
		try {
			String jsonService = gson.toJson(invoiceService.getAllInvoices());
			return new ResponseEntity<String>(jsonService, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/deleteInvoice")
	public ResponseEntity<Boolean> deleteInvoice(@RequestParam("id") Long invoiceId) throws Exception {
		Invoice invoice = new Invoice();
		try {
			invoice.setId(invoiceId);
			invoiceService.deleteInvoice(invoice);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping("/modifyInvoice")
	public ResponseEntity<Boolean> modifyInvoice(@RequestBody Invoice invoice) throws Exception {
		Boolean result = false;
		try {
			result = invoiceService.updateInvoice(invoice);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
		}
	}
}

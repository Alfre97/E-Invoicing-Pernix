package com.pernix.einvoicing.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, name="consecutive")
    private String consecutive;
	
	@Column(nullable=false, name="key")
    private String key;
    //Fecha de la factura en formato [yyyy-MM-dd'T'HH:mm:ssZ] como se define en [http://tools.ietf.org/html/rfc3339#section-5.6
	@Column(nullable=false, name="date")
    private String date;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitterId")
    private Emitter emitter;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId")
    private Receiver receiver;
	
	@Column(nullable=false, name="sellCodition")
    private String sellCondition;
	
	@Column(nullable=false, name="paymentLapse")
    private String paymentLapse;
	
	@Column(nullable=false, name="paymentMethod")
    private String paymentMethod;
	
	@Column(nullable=false, name="currency")
    private String currency;
	
	@Column(nullable=false, name="exchangeRate")
    private double exchangeRate;
	
	@Column(nullable=false, name="recordedServices")
    private double recordedServices;
	
	@Column(nullable=false, name="exemptServices")
    private double exemptServices;

	@Column(nullable=false, name="recordedCommodity")
    private double recordedCommodity;

	@Column(nullable=false, name="exemptCommodity")
    private double exemptCommodity;
	
	@Column(nullable=false, name="recordedTotal")
    private double recordedTotal;
	
	@Column(nullable=false, name="exemptTotal")
    private double exemptTotal;
	
	@Column(nullable=false, name="totalSell")
    private double totalSell;

	@Column(nullable=false, name="totalDiscount")
    private double totalDiscount;
	
	@Column(nullable=false, name="netSell")
    private double netSell;
	
	@Column(nullable=false, name="totalTax")
    private double totalTax;
	
	@Column(nullable=false, name="totalVoucher")
    private double totalVoucher;
	
	@Column(nullable=false, name="resolutionNumber")
    private String resolutionNumber;
	
	@Column(nullable=false, name="resolutionDate")
    private String resolutionDate;

	@Column(nullable=false, name="otherText")
    private String otherText;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "SERV_INV", joinColumns = @JoinColumn(name = "SERV_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "INV_ID", referencedColumnName = "ID"))
	private List<Services> serviceList;
    
	@Column(nullable=false, name="xmlInvoice")
    private String xmlInvoice;
	
	public Invoice() {}

	public Invoice(Long id, String consecutive, String key, String date, Emitter emitter, Receiver receiver,
			String sellCondition, String paymentLapse, String paymentMethod, String currency, double exchangeRate,
			double recordedServices, double exemptServices, double recordedCommodity, double exemptCommodity,
			double recordedTotal, double exemptTotal, double totalSell, double totalDiscount, double netSell,
			double totalTax, double totalVoucher, String resolutionNumber, String resolutionDate, String otherText,
			List<Services> serviceList, String xmlInvoice) {
		super();
		this.id = id;
		this.consecutive = consecutive;
		this.key = key;
		this.date = date;
		this.emitter = emitter;
		this.receiver = receiver;
		this.sellCondition = sellCondition;
		this.paymentLapse = paymentLapse;
		this.paymentMethod = paymentMethod;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.recordedServices = recordedServices;
		this.exemptServices = exemptServices;
		this.recordedCommodity = recordedCommodity;
		this.exemptCommodity = exemptCommodity;
		this.recordedTotal = recordedTotal;
		this.exemptTotal = exemptTotal;
		this.totalSell = totalSell;
		this.totalDiscount = totalDiscount;
		this.netSell = netSell;
		this.totalTax = totalTax;
		this.totalVoucher = totalVoucher;
		this.resolutionNumber = resolutionNumber;
		this.resolutionDate = resolutionDate;
		this.otherText = otherText;
		this.serviceList = serviceList;
		this.xmlInvoice = xmlInvoice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsecutive() {
		return consecutive;
	}

	public void setConsecutive(String consecutive) {
		this.consecutive = consecutive;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Emitter getEmitter() {
		return emitter;
	}

	public void setEmitter(Emitter emitter) {
		this.emitter = emitter;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public String getSellCondition() {
		return sellCondition;
	}

	public void setSellCondition(String sellCondition) {
		this.sellCondition = sellCondition;
	}

	public String getPaymentLapse() {
		return paymentLapse;
	}

	public void setPaymentLapse(String paymentLapse) {
		this.paymentLapse = paymentLapse;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getRecordedServices() {
		return recordedServices;
	}

	public void setRecordedServices(double recordedServices) {
		this.recordedServices = recordedServices;
	}

	public double getExemptServices() {
		return exemptServices;
	}

	public void setExemptServices(double exemptServices) {
		this.exemptServices = exemptServices;
	}

	public double getRecordedCommodity() {
		return recordedCommodity;
	}

	public void setRecordedCommodity(double recordedCommodity) {
		this.recordedCommodity = recordedCommodity;
	}

	public double getExemptCommodity() {
		return exemptCommodity;
	}

	public void setExemptCommodity(double exemptCommodity) {
		this.exemptCommodity = exemptCommodity;
	}

	public double getRecordedTotal() {
		return recordedTotal;
	}

	public void setRecordedTotal(double recordedTotal) {
		this.recordedTotal = recordedTotal;
	}

	public double getExemptTotal() {
		return exemptTotal;
	}

	public void setExemptTotal(double exemptTotal) {
		this.exemptTotal = exemptTotal;
	}

	public double getTotalSell() {
		return totalSell;
	}

	public void setTotalSell(double totalSell) {
		this.totalSell = totalSell;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getNetSell() {
		return netSell;
	}

	public void setNetSell(double netSell) {
		this.netSell = netSell;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalVoucher() {
		return totalVoucher;
	}

	public void setTotalVoucher(double totalVoucher) {
		this.totalVoucher = totalVoucher;
	}

	public String getResolutionNumber() {
		return resolutionNumber;
	}

	public void setResolutionNumber(String resolutionNumber) {
		this.resolutionNumber = resolutionNumber;
	}

	public String getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public List<Services> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Services> serviceList) {
		this.serviceList = serviceList;
	}

	public String getXmlInvoice() {
		return xmlInvoice;
	}

	public void setXmlInvoice(String xmlInvoice) {
		this.xmlInvoice = xmlInvoice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

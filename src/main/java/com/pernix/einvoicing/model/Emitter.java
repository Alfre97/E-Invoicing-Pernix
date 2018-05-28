package com.pernix.einvoicing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Emitter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, name="name")
	private String name;
	
	@Column(nullable=false, name="identificationType")
	private String identificationType;
	
	@Column(nullable=false, name="identificationNumber")
	private String identificationNumber;
	
	@Column(nullable=false, name="comercialName")
	private String comercialName;
	
	@Column(nullable=false, name="locationProvinceCode")
	private String locationProvinceCode;
	
	@Column(nullable=false, name="locationCantonCode")
	private String locationCantonCode;
	
	@Column(nullable=false, name="locationDistrictCode")
	private String locationDistrictCode;
	
	@Column(nullable=false, name="locationNeighborhoodCode")
	private String locationNeighborhoodCode;
	
	@Column(nullable=false, name="OtherSignals")
	private String otherSignals;
	
	@Column(nullable=false, name="phoneCountryCode")
	private String phoneCountryCode;
	
	@Column(nullable=false, name="phoneNumber")
	private String phoneNumber;
	
	@Column(nullable=false, name="faxCountryCode")
	private String faxCountryCode;
	
	@Column(nullable=false, name="faxNumber")
	private String faxNumber;
	
	@Column(nullable=false, name="email")
	private String email;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "emitterId")
	List<Invoice> invoiceList = new ArrayList<Invoice>();
	
	public Emitter() {}

	public Emitter(Long id, String name, String identificationType, String identificationNumber, String comercialName,
			String locationProvinceCode, String locationCantonCode, String locationDistrictCode,
			String locationNeighborhoodCode, String otherSignals, String phoneCountryCode, String phoneNumber,
			String faxCountryCode, String faxNumber, String email, List<Invoice> invoiceList) {
		super();
		this.id = id;
		this.name = name;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.comercialName = comercialName;
		this.locationProvinceCode = locationProvinceCode;
		this.locationCantonCode = locationCantonCode;
		this.locationDistrictCode = locationDistrictCode;
		this.locationNeighborhoodCode = locationNeighborhoodCode;
		this.otherSignals = otherSignals;
		this.phoneCountryCode = phoneCountryCode;
		this.phoneNumber = phoneNumber;
		this.faxCountryCode = faxCountryCode;
		this.faxNumber = faxNumber;
		this.email = email;
		this.invoiceList = invoiceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getComercialName() {
		return comercialName;
	}

	public void setComercialName(String comercialName) {
		this.comercialName = comercialName;
	}

	public String getLocationProvinceCode() {
		return locationProvinceCode;
	}

	public void setLocationProvinceCode(String locationProvinceCode) {
		this.locationProvinceCode = locationProvinceCode;
	}

	public String getLocationCantonCode() {
		return locationCantonCode;
	}

	public void setLocationCantonCode(String locationCantonCode) {
		this.locationCantonCode = locationCantonCode;
	}

	public String getLocationDistrictCode() {
		return locationDistrictCode;
	}

	public void setLocationDistrictCode(String locationDistrictCode) {
		this.locationDistrictCode = locationDistrictCode;
	}

	public String getLocationNeighborhoodCode() {
		return locationNeighborhoodCode;
	}

	public void setLocationNeighborhoodCode(String locationNeighborhoodCode) {
		this.locationNeighborhoodCode = locationNeighborhoodCode;
	}

	public String getOtherSignals() {
		return otherSignals;
	}

	public void setOtherSignals(String otherSignals) {
		this.otherSignals = otherSignals;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxCountryCode() {
		return faxCountryCode;
	}

	public void setFaxCountryCode(String faxCountryCode) {
		this.faxCountryCode = faxCountryCode;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

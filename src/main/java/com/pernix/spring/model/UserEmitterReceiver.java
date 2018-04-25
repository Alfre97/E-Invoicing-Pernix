package com.pernix.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEmitterReceiver implements Serializable {

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
	@Column(nullable=false, name="locationProvinceName")
	private String locationProvinceName;;
	@Column(nullable=false, name="locationCantonName")
	private String locationCantonName;
	@Column(nullable=false, name="locationDistrictName")
	private String locationDistrictName;
	@Column(nullable=false, name="locationNeighborhoodName")
	private String locationNeighborhoodName;
	@Column(nullable=false, name="OtherSignals")
	private String OtherSignals;
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
	@Column(nullable=false, name="userType")
	private String userType;
	
	
	public UserEmitterReceiver() {
		
	}

	public UserEmitterReceiver(Long id, String name, String identificationType, String identificationNumber,
			String comercialName, String locationProvinceType, String locationProvinceName, String locationCantonType,
			String locationCantonName, String locationDistrictType, String locationDistrictName,
			String locationNeighborhoodType, String locationNeighborhoodName, String otherSignals,
			String phoneCountryCode, String phoneNumber, String faxCountryCode, String faxNumber, String email,
			String userType) {
		this.id = id;
		this.name = name;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.comercialName = comercialName;
		this.locationProvinceName = locationProvinceName;
		this.locationCantonName = locationCantonName;
		this.locationDistrictName = locationDistrictName;
		this.locationNeighborhoodName = locationNeighborhoodName;
		OtherSignals = otherSignals;
		this.phoneCountryCode = phoneCountryCode;
		this.phoneNumber = phoneNumber;
		this.faxCountryCode = faxCountryCode;
		this.faxNumber = faxNumber;
		this.email = email;
		this.userType = userType;
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

	public String getLocationProvinceName() {
		return locationProvinceName;
	}

	public void setLocationProvinceName(String locationProvinceName) {
		this.locationProvinceName = locationProvinceName;
	}

	public String getLocationCantonName() {
		return locationCantonName;
	}

	public void setLocationCantonName(String locationCantonName) {
		this.locationCantonName = locationCantonName;
	}

	public String getLocationDistrictName() {
		return locationDistrictName;
	}

	public void setLocationDistrictName(String locationDistrictName) {
		this.locationDistrictName = locationDistrictName;
	}

	public String getLocationNeighborhoodName() {
		return locationNeighborhoodName;
	}

	public void setLocationNeighborhoodName(String locationNeighborhoodName) {
		this.locationNeighborhoodName = locationNeighborhoodName;
	}

	public String getOtherSignals() {
		return OtherSignals;
	}

	public void setOtherSignals(String otherSignals) {
		OtherSignals = otherSignals;
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
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}

package com.pernix.entity;

public class User {
	private Integer Id;
	private String name;
	private String identificationType;
	private String identificationNumber;
	private String comercialName;
	private String locationProvinceType;
	private String locationProvinceName;
	private String locationCantonType;
	private String locationCantonName;
	private String locationDistrictType;
	private String locationDistrictName;
	private String locationNeighborhoodType;
	private String locationNeighborhoodName;
	private String OtherSignals;
	private String phoneCountryCode;
	private String phoneNumber;	
	private String faxCountryCode;
	private String faxNumber;
	private String email;
	
	public User() {
		
	}
	
	public User(Integer id, String name, String identificationType, String identificationNumber, String comercialName,
			String locationProvinceType, String locationProvinceName, String locationCantonType,
			String locationCantonName, String locationDistrictType, String locationDistrictName,
			String locationNeighborhoodType, String locationNeighborhoodName, String otherSignals,
			String phoneCountryCode, String phoneNumber, String faxCountryCode, String faxNumber, String email) {
		super();
		Id = id;
		this.name = name;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.comercialName = comercialName;
		this.locationProvinceType = locationProvinceType;
		this.locationProvinceName = locationProvinceName;
		this.locationCantonType = locationCantonType;
		this.locationCantonName = locationCantonName;
		this.locationDistrictType = locationDistrictType;
		this.locationDistrictName = locationDistrictName;
		this.locationNeighborhoodType = locationNeighborhoodType;
		this.locationNeighborhoodName = locationNeighborhoodName;
		OtherSignals = otherSignals;
		this.phoneCountryCode = phoneCountryCode;
		this.phoneNumber = phoneNumber;
		this.faxCountryCode = faxCountryCode;
		this.faxNumber = faxNumber;
		this.email = email;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
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
	public String getLocationProvinceType() {
		return locationProvinceType;
	}
	public void setLocationProvinceType(String locationProvinceType) {
		this.locationProvinceType = locationProvinceType;
	}
	public String getLocationProvinceName() {
		return locationProvinceName;
	}
	public void setLocationProvinceName(String locationProvinceName) {
		this.locationProvinceName = locationProvinceName;
	}
	public String getLocationCantonType() {
		return locationCantonType;
	}
	public void setLocationCantonType(String locationCantonType) {
		this.locationCantonType = locationCantonType;
	}
	public String getLocationCantonName() {
		return locationCantonName;
	}
	public void setLocationCantonName(String locationCantonName) {
		this.locationCantonName = locationCantonName;
	}
	public String getLocationDistrictType() {
		return locationDistrictType;
	}
	public void setLocationDistrictType(String locationDistrictType) {
		this.locationDistrictType = locationDistrictType;
	}
	public String getLocationDistrictName() {
		return locationDistrictName;
	}
	public void setLocationDistrictName(String locationDistrictName) {
		this.locationDistrictName = locationDistrictName;
	}
	public String getLocationNeighborhoodType() {
		return locationNeighborhoodType;
	}
	public void setLocationNeighborhoodType(String locationNeighborhoodType) {
		this.locationNeighborhoodType = locationNeighborhoodType;
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
	public String getPhoneCountryName() {
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
	public String getFaxCountryName() {
		return faxNumber;
	}
	public void setfaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

package com.pernix.entity;

public class Service {
	private String lineNumber;
	private String codeType;
	private String code;
	private String amount;
	private String unitOfMeasurementType;
	private String unitOfMeasurementName;
	private String comercialUnitOfMeasurement;
	private String detail;
	private String priceByUnit;
	private String totalAmount;
	private String discount;
	private String subTotal;
	private String total;
	
	public Service() {
		
	}
	
	public Service(String lineNumber, String codeType, String code, String amount, String unitOfMeasurementType,
			String unitOfMeasurementName, String comercialUnitOfMeasurement, String detail, String priceByUnit,
			String totalAmount, String discount, String subTotal, String total) {
		super();
		this.lineNumber = lineNumber;
		this.codeType = codeType;
		this.code = code;
		this.amount = amount;
		this.unitOfMeasurementType = unitOfMeasurementType;
		this.unitOfMeasurementName = unitOfMeasurementName;
		this.comercialUnitOfMeasurement = comercialUnitOfMeasurement;
		this.detail = detail;
		this.priceByUnit = priceByUnit;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.subTotal = subTotal;
		this.total = total;
	}
	
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnitOfMeasurementType() {
		return unitOfMeasurementType;
	}
	public void setUnitOfMeasurementType(String unitOfMeasurementType) {
		this.unitOfMeasurementType = unitOfMeasurementType;
	}
	public String getUnitOfMeasurementName() {
		return unitOfMeasurementName;
	}
	public void setUnitOfMeasurementName(String unitOfMeasurementName) {
		this.unitOfMeasurementName = unitOfMeasurementName;
	}
	public String getComercialUnitOfMeasurement() {
		return comercialUnitOfMeasurement;
	}
	public void setComercialUnitOfMeasurement(String comercialUnitOfMeasurement) {
		this.comercialUnitOfMeasurement = comercialUnitOfMeasurement;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPriceByUnit() {
		return priceByUnit;
	}
	public void setPriceByUnit(String priceByUnit) {
		this.priceByUnit = priceByUnit;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}

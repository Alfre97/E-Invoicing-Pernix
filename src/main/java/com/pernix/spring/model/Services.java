package com.pernix.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Services implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, name="lineNumber")
	private String lineNumber;	
	
	@OneToMany(
	        mappedBy = "service", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	List<Code> codeList = new ArrayList<Code>();
	
	@Column(nullable=false, name="amount")
	private String amount;
	
	@Column(nullable=false, name="unitOfMeasurementType")
	private String unitOfMeasurementType;
	
	@Column(nullable=false, name="unitOfMeasurementName")
	private String unitOfMeasurementName;
	
	@Column(nullable=false, name="comercialUnitOfMeasurement")
	private String comercialUnitOfMeasurement;
	
	@Column(nullable=false, name="detail")
	private String detail;
	
	@Column(nullable=false, name="priceByUnit")
	private String priceByUnit;
	
	@Column(nullable=false, name="totalAmount")
	private String totalAmount;
	
	@Column(nullable=false, name="discount")
	private String discount;
	
	@Column(nullable=false, name="discountNature")
	private String discountNature;
	
	@Column(nullable=false, name="subTotal")
	private String subTotal;
	
	@OneToMany(
	        mappedBy = "service", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	List<Tax> taxList = new ArrayList<Tax>();
	
	@Column(nullable=false, name="total")
	private String total;
	
	public Services() {}

	public Services(Long id, String lineNumber, List<Code> codeList, String amount, String unitOfMeasurementType,
			String unitOfMeasurementName, String comercialUnitOfMeasurement, String detail, String priceByUnit,
			String totalAmount, String discount, String discountNature, String subTotal, List<Tax> taxList,
			String total) {
		super();
		this.id = id;
		this.lineNumber = lineNumber;
		this.codeList = codeList;
		this.amount = amount;
		this.unitOfMeasurementType = unitOfMeasurementType;
		this.unitOfMeasurementName = unitOfMeasurementName;
		this.comercialUnitOfMeasurement = comercialUnitOfMeasurement;
		this.detail = detail;
		this.priceByUnit = priceByUnit;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.discountNature = discountNature;
		this.subTotal = subTotal;
		this.taxList = taxList;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
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

	public String getDiscountNature() {
		return discountNature;
	}

	public void setDiscountNature(String discountNature) {
		this.discountNature = discountNature;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public List<Tax> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<Tax> taxList) {
		this.taxList = taxList;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

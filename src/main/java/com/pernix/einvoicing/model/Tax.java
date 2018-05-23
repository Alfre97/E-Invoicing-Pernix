package com.pernix.einvoicing.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "code")
	private String code;

	@Column(nullable = true, name = "taxTotal")
	private double taxTotal;

	@Column(nullable = false, name = "rate")
	private double rate;

	@Column(nullable = true, name = "date")
	private String date;

	@Column(nullable = true, name = "taxExonarated")
	private double taxExonarated;

	@Column(nullable = true, name = "institutionName")
	private String institutionName;

	@Column(nullable = true, name = "documentNumber")
	private String documentNumber;

	@Column(nullable = false, name = "purchasePercentage")
	private int purchasePercentage;

	@Column(nullable = true, name = "documentType")
	private String documentType;

	public Tax() {
	}

	public Tax(Long id, String code, double taxTotal, double rate, String date, double taxExonarated,
			String institutionName, String documentNumber, int purchasePercentage, String documentType) {
		super();
		this.id = id;
		this.code = code;
		this.taxTotal = taxTotal;
		this.rate = rate;
		this.date = date;
		this.taxExonarated = taxExonarated;
		this.institutionName = institutionName;
		this.documentNumber = documentNumber;
		this.purchasePercentage = purchasePercentage;
		this.documentType = documentType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTaxExonarated() {
		return taxExonarated;
	}

	public void setTaxExonarated(double taxExonarated) {
		this.taxExonarated = taxExonarated;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public int getPurchasePercentage() {
		return purchasePercentage;
	}

	public void setPurchasePercentage(int purchasePercentage) {
		this.purchasePercentage = purchasePercentage;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

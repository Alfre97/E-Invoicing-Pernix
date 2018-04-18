package com.pernix.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Code {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false, name="codeType")
	private String codeType;
	
	@Column(nullable=false, name="code")
	private String code;
	
	private Service serviceCode;
	
	public Code() {
		
	}

	public Code(Integer id, String codeType, String code, Service serviceCode) {
		super();
		this.id = id;
		this.codeType = codeType;
		this.code = code;
		this.serviceCode = serviceCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@ManyToOne
	public Service getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(Service serviceCode) {
		this.serviceCode = serviceCode;
	}
}
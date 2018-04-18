package com.pernix.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Code {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false, name="codeType")
	private String codeType;
	@Column(nullable=false, name="code")
	private String code;
	@Column(nullable=true, name="serviceId")
	private Integer serviceId;
	
	public Code() {
		
	}

	public Code(Integer id, String codeType, String code, int serviceId) {
		super();
		this.id = id;
		this.codeType = codeType;
		this.code = code;
		this.serviceId = serviceId;
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

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}	
}
package com.pernix.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class XMLInvoice {
	private long Clave;
	private long NumeroConsecutivo;
	private Date FechaEmision;
	
}

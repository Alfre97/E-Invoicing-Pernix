/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pernix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.pernix.controller.InvoiceController;
import com.pernix.entity.Identification;
import com.pernix.entity.Invoice;

@SpringBootApplication
public class EInvoiceApp{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EInvoiceApp.class, args);
        Invoice invoice = new Invoice();
    	Identification idEmitter = new com.pernix.entity.Identification("01", "136526987");
    	Identification idRecipient = new Identification("02", "3001123208");
    	
    	invoice.setKey("50612101700020586086000100001010000000161100000642");
    	invoice.setDate("2017-12-07T11:47:42.539375-06:00");
    	invoice.setEmitter(idEmitter);
    	invoice.setRecipient(idRecipient);
    	invoice.setXmlInvoice("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
    			"<FacturaElectronica xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/facturaElectronica\">\r\n" + 
    			"    <Clave>50612101700020586086000100001010000000161100000642</Clave>\r\n" + 
    			"    <NumeroConsecutivo>00100001010000000162</NumeroConsecutivo>\r\n" + 
    			"    <FechaEmision>2017-12-07T11:47:42.539375-06:00</FechaEmision>\r\n" + 
    			"    <Emisor>\r\n" + 
    			"        <Nombre>Jhon Moreira Baltodano</Nombre>\r\n" + 
    			"        <Identificacion>\r\n" + 
    			"            <Tipo>01</Tipo>\r\n" + 
    			"            <Numero>136526987</Numero>\r\n" + 
    			"        </Identificacion>\r\n" + 
    			"        <NombreComercial>SolutionsCR</NombreComercial>\r\n" + 
    			"        <Ubicacion>\r\n" + 
    			"            <Provincia>4</Provincia>\r\n" + 
    			"            <Canton>09</Canton>\r\n" + 
    			"            <Distrito>01</Distrito>\r\n" + 
    			"            <Barrio>01</Barrio>\r\n" + 
    			"            <OtrasSenas>SanJose, Guadalupe</OtrasSenas>\r\n" + 
    			"        </Ubicacion>\r\n" + 
    			"        <Telefono>\r\n" + 
    			"            <CodigoPais>506</CodigoPais>\r\n" + 
    			"            <NumTelefono>88768987</NumTelefono>\r\n" + 
    			"        </Telefono>\r\n" + 
    			"        <Fax>\r\n" + 
    			"            <CodigoPais>506</CodigoPais>\r\n" + 
    			"            <NumTelefono>00000000</NumTelefono>\r\n" + 
    			"        </Fax>\r\n" + 
    			"        <CorreoElectronico>jonh.m.10@gmail.com</CorreoElectronico>\r\n" + 
    			"    </Emisor>\r\n" + 
    			"    <Receptor>\r\n" + 
    			"        <Nombre>Dental Care</Nombre>\r\n" + 
    			"        <Identificacion>\r\n" + 
    			"            <Tipo>02</Tipo>\r\n" + 
    			"            <Numero>3001123208</Numero>\r\n" + 
    			"        </Identificacion>\r\n" + 
    			"        <NombreComercial />\r\n" + 
    			"        <Ubicacion>\r\n" + 
    			"            <Provincia>1</Provincia>\r\n" + 
    			"            <Canton>01</Canton>\r\n" + 
    			"            <Distrito>01</Distrito>\r\n" + 
    			"            <Barrio>01</Barrio>\r\n" + 
    			"            <OtrasSenas />\r\n" + 
    			"        </Ubicacion>\r\n" + 
    			"        <Telefono>\r\n" + 
    			"            <CodigoPais>506</CodigoPais>\r\n" + 
    			"            <NumTelefono>88888888</NumTelefono>\r\n" + 
    			"        </Telefono>\r\n" + 
    			"        <Fax>\r\n" + 
    			"            <CodigoPais>506</CodigoPais>\r\n" + 
    			"            <NumTelefono>506</NumTelefono>\r\n" + 
    			"        </Fax>\r\n" + 
    			"        <CorreoElectronico>info@dentalcare.com</CorreoElectronico>\r\n" + 
    			"    </Receptor>\r\n" + 
    			"    <CondicionVenta>02</CondicionVenta>\r\n" + 
    			"    <PlazoCredito>15</PlazoCredito>\r\n" + 
    			"    <MedioPago>04</MedioPago>\r\n" + 
    			"    <DetalleServicio>\r\n" + 
    			"        <LineaDetalle>\r\n" + 
    			"            <NumeroLinea>1</NumeroLinea>\r\n" + 
    			"            <Codigo>\r\n" + 
    			"                <Tipo>04</Tipo>\r\n" + 
    			"                <Codigo>3</Codigo>\r\n" + 
    			"            </Codigo>\r\n" + 
    			"            <Cantidad>1.000</Cantidad>\r\n" + 
    			"            <UnidadMedida>Unid</UnidadMedida>\r\n" + 
    			"            <UnidadMedidaComercial />\r\n" + 
    			"            <Detalle>Servicios profesionales</Detalle>\r\n" + 
    			"            <PrecioUnitario>150000.00000</PrecioUnitario>\r\n" + 
    			"            <MontoTotal>150000.00000</MontoTotal>\r\n" + 
    			"            <NaturalezaDescuento />\r\n" + 
    			"            <SubTotal>150000.00000</SubTotal>\r\n" + 
    			"            <MontoTotalLinea>150000.00000</MontoTotalLinea>\r\n" + 
    			"        </LineaDetalle>\r\n" + 
    			"    </DetalleServicio>\r\n" + 
    			"    <ResumenFactura>\r\n" + 
    			"        <CodigoMoneda>USD</CodigoMoneda>\r\n" + 
    			"        <TipoCambio>576.74000</TipoCambio>\r\n" + 
    			"        <TotalServGravados>0.00000</TotalServGravados>\r\n" + 
    			"        <TotalServExentos>150000.00000</TotalServExentos>\r\n" + 
    			"        <TotalMercanciasGravadas>0.00000</TotalMercanciasGravadas>\r\n" + 
    			"        <TotalMercanciasExentas>0.00000</TotalMercanciasExentas>\r\n" + 
    			"        <TotalGravado>0.00000</TotalGravado>\r\n" + 
    			"        <TotalExento>150000.00000</TotalExento>\r\n" + 
    			"        <TotalVenta>150000.00000</TotalVenta>\r\n" + 
    			"        <TotalDescuentos>0.00000</TotalDescuentos>\r\n" + 
    			"        <TotalVentaNeta>150000.00000</TotalVentaNeta>\r\n" + 
    			"        <TotalImpuesto>0.00000</TotalImpuesto>\r\n" + 
    			"        <TotalComprobante>150000.00000</TotalComprobante>\r\n" + 
    			"    </ResumenFactura>\r\n" + 
    			"    <Normativa>\r\n" + 
    			"        <NumeroResolucion>DGT-R-48-2016</NumeroResolucion>\r\n" + 
    			"        <FechaResolucion>20-02-2017 13:22:22</FechaResolucion>\r\n" + 
    			"    </Normativa>\r\n" + 
    			"    <Otros>\r\n" + 
    			"        <OtroTexto codigo=\"obs\">BNCR $ 200-</OtroTexto>\r\n" + 
    			"    </Otros>\r\n" + 
    			"</FacturaElectronica>");
    	
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> parameters = new LinkedMultiValueMap<String,String>();
        parameters.add("key", invoice.getKey());
        parameters.add("emitterId", invoice.getEmitter().getId());
        parameters.add("emitterType", invoice.getEmitter().getType());
        parameters.add("recipientId", invoice.getEmitter().getId());
        parameters.add("recipientType", invoice.getEmitter().getType());
        parameters.add("xmlInvoice", invoice.getXmlInvoice());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        InvoiceController ic= new InvoiceController();
        ic.uploadInvoice(invoice);
    }
}

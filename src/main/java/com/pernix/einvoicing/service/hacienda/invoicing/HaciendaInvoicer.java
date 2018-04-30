package com.pernix.einvoicing.service.hacienda.invoicing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pernix.einvoicing.model.Invoice;
import com.pernix.einvoicing.service.hacienda.oauth2.*;
import com.pernix.einvoicing.service.hacienda.signing.InvoiceSigner;

@Service
public class HaciendaInvoicer implements InvoicerService {

    @Autowired
    OAuth2Service HaciendaOAuth2Service;
    private static final String URI = "https://api.comprobanteselectronicos.go.cr/recepcion-sandbox/v1/";

    @Override
    public String save(Invoice invoice) {
        String invoicePayload = buildInvoicePayload(invoice);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI + "recepcion");
        Invocation.Builder request = target.request();

        // Se deberá brindar una cabecera (header) "Authorization" con el valor del access token obtenido anteriormente.
        request.header("Authorization", "Bearer " + HaciendaOAuth2Service.getAccessToken());

        // Se envía un POST. con los datos del documento que deseamos registrar, observe que colocamos como  
        // atributo el objeto que configuramos en el apartado de 'Preparación'
        Response response = request.post(Entity.json(invoicePayload));

        switch (response.getStatus()) {
          case 201:
            // Éste código de retorno se da por recibido a la plataforma el documento. Posteriormente
            // debe validarse su estado de aceptación o rechazo. Es importante hacer notar que se
            // regresa un header "Location" que corresponde a un URL. donde se puede validar el
            // estado del documento, por ejemplo:
            // https://api.comprobanteselectronicos.go.cr/recepcion-sandbox/v1/recepcion/50601011600310112345600100010100000000011999999999/
            break;
          case 400:
            // Se da si se detecta un error en las validaciones, por ejemplo: si intento enviar más de una
            // vez un documento. El encabezado "X-Error-Cause" indica la causa del problema.
            String xErrorCause = response.getHeaderString("X-Error-Cause");
           // LOG.log(Level.SEVERE, xErrorCause);
            break;
          }
        return null;
    }
    
    private Invoice signXML(Invoice invoice) {
    	InvoiceSigner signer = new InvoiceSigner();
    invoice.setXmlInvoice(signer.sign("certificate/011417047734.p12", "7410", invoice.getXmlInvoice()));
    	return invoice;
    }

    private String buildInvoicePayload(Invoice invoice){
    		signXML(invoice);
    		Gson gson = new Gson();
    		String json = gson.toJson(invoice);
    		
        return json;
    }

	@Override
	public String get() {
		// TODO Auto-generated method stub
		return null;
	}
}

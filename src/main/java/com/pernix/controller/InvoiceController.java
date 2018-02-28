package com.pernix.controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pernix.entity.Invoice;
import com.pernix.service.hacienda.invoicing.InvoicerService;

@RestController
@RequestMapping("api/v1")
public class InvoiceController {

    @Autowired
    InvoicerService HaciendaInvoicer;

    @RequestMapping(value="uploadInvoice", method = RequestMethod.POST)
    public Response uploadInvoice(Invoice invoice){
        String locationValue = HaciendaInvoicer.save(invoice);
        if(StringUtils.isEmpty(locationValue))
            return Response.status(400).build();
        JsonObject jsonResponse = Json.createObjectBuilder().add("message", "Invoice under validation").build();
        return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
    }
}

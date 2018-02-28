package com.pernix;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.json.Json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class InvoiceControllerWebIntegrationTest {

    @Test
    public void testInvoiceControllerSaveInvoice(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Json> response = restTemplate.postForEntity("http://localhost:5000/api/v1/uploadInvoice", null, Json.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}

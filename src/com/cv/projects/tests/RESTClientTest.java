package com.cv.projects.tests;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.cv.projects.app.RESTClient;
import com.cv.projects.services.CustomerData;

public class RESTClientTest {
	  
    public static final int HTTP_CREATED = 201;
    public static final int OK = 201;
    private RESTClient client = new RESTClient();
 
    @Test
    public void givenCorrectObject_whenCorrectJsonRequest_thenResponseCodeCreated() {
        CustomerData cd = new CustomerData();
        cd.setName("abc");
        cd.setDob("198841508");
        cd.setCountry("Neverland");
        cd.setAddress("501 MonzenNakacho");
        cd.setPhoneno("88239");
 
        Response response = client.createJsonCustomer(cd);
//        Response response = client.createURLCustomer(cd);
        
        assertEquals(response.getStatus(), HTTP_CREATED);
        
        CustomerData cd_got = client.getJsonCustomer(5);
        
        System.out.println(cd_got.getId());
		System.out.println(cd_got.getName());
		System.out.println(cd_got.getDob());
		System.out.println(cd_got.getCountry());
		System.out.println(cd_got.getPhoneno());
    }
}
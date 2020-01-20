package com.cv.projects.app;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cv.projects.services.CustomerData;

import javax.ws.rs.client.Client;

public class RESTClient {
//	public static void main(String[] args) {
//		Client client = ClientBuilder.newClient();
//		WebTarget target = client.target("http://localhost:8080/rest/customer/create");  // or rest/customer/{id}
//		Invocation.Builder invocationBuilder 
//		  = target.request(MediaType.APPLICATION_JSON);
//		String s = target.request().get(String.class);
//		System.out.println(s);
//	}
	
	
    private static final String REST_URI 
    = "http://localhost:8080/rest/customers";

	  private Client client = ClientBuilder.newClient();
	
	  public CustomerData getJsonCustomer(int id) {
	      return client
	        .target(REST_URI)
	        .path(String.valueOf(id))
	        .request(MediaType.APPLICATION_JSON)
	        .get(CustomerData.class);
	  }
	  
	  public Response createJsonCustomer(CustomerData cd) {
		    return client
		      .target(REST_URI+"/")
		      .request(MediaType.APPLICATION_JSON)
		      .post(Entity.entity(cd, MediaType.APPLICATION_JSON));
		}
	  
	  public Response createXmlCustomer(CustomerData cd) {
	        return client
	        		.target(REST_URI+"/")
	        		.request(MediaType.APPLICATION_XML)
	        		.post(Entity.entity(cd, MediaType.APPLICATION_XML));
	    }

	    public CustomerData getXmlCustomer(int id) {
	        return client
	        		.target(REST_URI)
	        		.path(String.valueOf(id))
	        		.request(MediaType.APPLICATION_XML)
	        		.get(CustomerData.class);
	    }
	    
	    public Response createURLCustomer(CustomerData cd) {
	        return client
	        		.target(REST_URI+"/")
	        		.request(MediaType.APPLICATION_FORM_URLENCODED)
	        		.post(Entity.entity(cd, MediaType.APPLICATION_FORM_URLENCODED));
	    }

}

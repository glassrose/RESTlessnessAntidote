package com.cv.projects.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.Integer;
import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;

//Java program to calculate SHA-512 hash value - unused

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.IllegalFormatException; 

class Encryption { 
	public static String encryptThisString(String input) 
	{ 
		try { 
			// getInstance() method is called with algorithm SHA-512 
			MessageDigest md = MessageDigest.getInstance("SHA-512"); 

			// digest() method is called 
			// to calculate message digest of the input string 
			// returned as array of byte 
			byte[] messageDigest = md.digest(input.getBytes()); 

			// Convert byte array into signum representation 
			BigInteger no = new BigInteger(1, messageDigest); 

			// Convert message digest into hex value 
			String hashtext = no.toString(16); 

			// Add preceding 0s to make it 32 bit 
			while (hashtext.length() < 32) { 
				hashtext = "0" + hashtext; 
			} 

			// return the HashText 
			return hashtext; 
		} 

		// For specifying wrong message digest algorithms 
		catch (NoSuchAlgorithmException e) { 
			throw new RuntimeException(e); 
		} 
	} 
} 


@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class Produces_XML_JSON_Example 
{	
	@Path("/{id}")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCustomerById(@PathParam("id") int user_id) {
		final String querySql =
				"SELECT * FROM customers WHERE user_id="+user_id;
		
    	Connection conn = getConnection();
    	
    	assert(conn != null);
    	
    	CustomerData cd = new CustomerData();
    	try (Statement stmt = conn.createStatement();
    		 ResultSet rs = stmt.executeQuery(querySql);) {
    	
	        if(rs.next()){
	           //Retrieve by column name
	           String name = rs.getString("name");
	           String dob = rs.getString("dob");
	           String country = rs.getString("country");
	           String address = rs.getString("address");
	           String phoneno = rs.getString("phoneno");


//	           Response resp = Response.ok(Response.Status.OK).entity(cd).build();
//	           return resp;
	           
	            cd = new CustomerData();
	           	cd.setId(user_id);
	           	cd.setName(name);
	          	cd.setDob(dob);
	          	cd.setCountry(country);
	           	cd.setAddress(address);
	           	cd.setPhoneno(phoneno);
//	           	GenericEntity<CustomerData> gecd = new GenericEntity<CustomerData> (cd) {
//			        };
			           
//	    	    Response resp = Response.ok(gecd).build();
//	    	    return resp;
			        
//	            return cd;
	            return Response.ok(Response.Status.OK).entity(cd).build();
			        
	        }
    	} catch (SQLException e) {
    		System.out.println("SQL Error");
    		e.printStackTrace();
//  		  	return Response.status(Response.Status.BAD_REQUEST).entity("SQL Exception: "+e.getMessage()+"\n"+e.getSQLState()).build();
    	}
    	
//    	return cd;
    	return Response.status(Response.Status.NOT_FOUND).entity(cd).build();

	}
    
    @Path("/post") // Not needed as its empty
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Response postCustomerData(@FormParam("name") String name,
    		@FormParam("dob_yyyymmdd") String dob,
    		@FormParam("country") String country,
    		@FormParam("address") String address,
    		@FormParam("phone_number") String phoneno) {

    	if (name==null || dob==null || country==null || address==null || phoneno==null) {
    		return Response.status(Response.Status.BAD_REQUEST).entity("Required field missing!").build();
    	}
    	if (dob.compareTo("")==0) {  /*|| dob==null <= this never happens, even if the parameter isn't passed at all*/
    		return Response.status(Response.Status.LENGTH_REQUIRED).entity("DOB can't be empty!").build();
    	}
    	if (name.compareTo("")==0) {
    		return Response.status(Response.Status.LENGTH_REQUIRED).entity("Name can't be empty!").build();
    	}
    	//validate dob format
    	//validate phoneno's size (no more than 20 chars)
		  //return malformed input error response
    	//Can there be more than 1 record with same values? I assume yes, hence no surrogate key. Actual Primary Key is returned.
    	
    	Connection conn = null;
    	
    	if (conn == null)
    		conn = getConnection();
    	
    	assert(conn != null);
    	
    	
    	final String createPostSql =
    		    "INSERT INTO customers (name, dob, country, address, phoneno) VALUES (?, ?, ?, ?, ?)";
    	
    	// Build the SQL command to insert the customer record into the database
    	  try (PreparedStatement statementCreatePost =
    			  conn.prepareStatement(createPostSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    	    // set the author to the user ID from the user table
    	    statementCreatePost.setString(1, name);
    	    statementCreatePost.setString(2, dob);
    	    statementCreatePost.setString(3, country);
    	    statementCreatePost.setString(4, address);
    	    statementCreatePost.setString(5, phoneno);
    	    int affectedRows = statementCreatePost.executeUpdate();
    	    
    	    
    	    //MySQL supports returning generated keys, Statement.RETURN_GENERATED_KEYS, and getGeneratedKeys()
    	    if (affectedRows == 0) {
    	    	throw new IOException("No rows inserted");
    	    }
    	    
    	    int res = -1;
    	    try (ResultSet generatedKeys = statementCreatePost.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    res = generatedKeys.getInt(1);
                }
                else {
                    throw new IOException("Creating user failed, no ID obtained.");
                }
            }
    	    if (res == -1) {
    	    	throw new IOException ("No ID obtained");
    	    }

    	    conn.close(); // can throw exception itself, hence here
    	    
    	    return Response.status(Response.Status.CREATED).entity(res).build();
    	    
    	    
    	  } catch (IOException e) {
    		  System.out.println("Internal failure");
    		  e.printStackTrace();
		  } catch (SQLException e) {
			  System.out.println("SQL Error");
    		  e.printStackTrace();
		  } catch (IllegalFormatException e) {
			  System.out.println("Invalid input format");
    		  e.printStackTrace();
		  }
    	  
		  return Response.status(Response.Status.BAD_REQUEST).build();
    }

	private Connection getConnection() {
		final String createCustomersTableSql =
			    "CREATE TABLE IF NOT EXISTS customers ( user_id INT NOT NULL AUTO_INCREMENT, "
			            + "name VARCHAR(64) NOT NULL, "
			    		+ "dob DATE NOT NULL, "
			    		+ "country VARCHAR(64), "
			    		+ "address VARCHAR(255), "
			    		+ "phoneno VARCHAR(20), "
			            + "PRIMARY KEY (user_id) )";

		Connection conn = null;
		  try {
			    String url = System.getProperty("cloudsql");

			    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				    if (url == null) {
				    	try {
				            Class.forName("com.mysql.jdbc.GoogleDriver");
				        	url = "jdbc:google:mysql://testjaxbjersey:asia-south1:test-rest-jersey-crud/Customers?user=chandniverma&password=mylove";
				        } catch (ClassNotFoundException e) {
				            e.getStackTrace().toString();
				            e.printStackTrace();
				        }
				    }
				    if (url == null) {
				    	try {
				            Class.forName("com.mysql.jdbc.GoogleDriver");
				        	url = "jdbc:mysql://google/Customers?cloudSqlInstance=testjaxbjersey:asia-south1:test-rest-jersey-crud&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=chandniverma&password=mylove";
				        } catch (ClassNotFoundException e) {
				            e.getStackTrace().toString();
				            e.printStackTrace();
				        }
				    }
			    }
			    
			    if (url == null) {
			    	try {
//			            Class.forName("com.mysql.jdbc.Driver");
			            Class.forName("com.mysql.cj.jdbc.Driver");
			        	url = "jdbc:mysql://localhost:3306/localDB?user=root&password=myloveA0509";
			        } catch (ClassNotFoundException e) {
			            e.getStackTrace().toString();
			            e.printStackTrace();
			        }
//			    	url = "mysql://chandniverma:mylove@/DB2?unix_socket=/cloudsql/gaeplugincloudsql:asia-south1:my-cloud-mysql";
//			    	url = "jdbc:google:mysql://gaeplugincloudsql:asia-south1:my-cloud-mysql/DB2?user=chandniverma&amp;password=mylove";
			    }

		    	System.out.println("Connecting to db at : " + url);


				try {
					conn = DriverManager.getConnection(url);
					conn.createStatement().executeUpdate(createCustomersTableSql);
					
				} catch (SQLException e) {
					System.out.println("Unable to connect to SQL server");
					e.printStackTrace();
				}
				return conn;
				
		  } finally {
		    // Nothing really to do here.
		  }
		  
	} // end of getConnection()
	
    
    
}

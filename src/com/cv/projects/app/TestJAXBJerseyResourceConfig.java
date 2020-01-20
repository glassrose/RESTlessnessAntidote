package com.cv.projects.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class TestJAXBJerseyResourceConfig extends ResourceConfig{
	public TestJAXBJerseyResourceConfig () {
        // Define the package which contains the service classes.
        packages("com.cv.projects.services");
        
        //Register JACKSON Feature class
        register(JacksonFeature.class);
    }
}

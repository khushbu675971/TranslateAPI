package com.app.translate;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {
	Config config = new Config("config.properties");
	
    @Override
    public void filter(ContainerRequestContext request,
            ContainerResponseContext response) throws IOException {
    	
    	String origins = config.getProperty("origins");
    	response.getHeaders().add("Access-Control-Allow-Origin", origins);
        response.getHeaders().add("Access-Control-Allow-Headers", "origin");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST");
    }
}

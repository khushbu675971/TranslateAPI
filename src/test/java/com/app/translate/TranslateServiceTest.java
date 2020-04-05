package com.app.translate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.glassfish.jersey.server.ResourceConfig;

public class TranslateServiceTest extends JerseyTest {

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TranslateService.class);
    }
	
	@Before
	public void setUpChild() { }

	@After
	public void tearDownChild() { }
    
	
	@Test
	public void getTranslationWhenParamProvided() {
		Form form = new Form();
		form.param("sourcelang", "en-US");
		form.param("targetlang", "cs-CZ");
		form.param("text", "Hello World");
	    Response response = target("/translate").request().post(Entity.form(form));
	 
	    assertEquals("Http Response should be 200 ", Status.OK.getStatusCode(), response.getStatus());
	    assertThat(response.readEntity(String.class), equalTo("Ahoj světe"));
	}
	
	@Test
	public void badRequest_WhenSourceLangNotPresent() {
		Form form = new Form();
		form.param("targetlang", "en-US");
		form.param("text", "Hello World");
	    Response response = target("/translate").request().post(Entity.form(form));
	 
	    assertEquals("Http Response should be 400 ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	    assertThat(response.readEntity(String.class), equalTo("Please provide source language"));
	}
	
	@Test
	public void badRequest_WhenTargetLangNotPresent() {
		Form form = new Form();
		form.param("sourcelang", "en-US");
		form.param("text", "Hello World");
		Response response = target("/translate").request().post(Entity.form(form));
	 
	    assertEquals("Http Response should be 400 ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	    assertThat(response.readEntity(String.class), equalTo("Please provide target language"));
	}
	
	@Test
	public void badRequest_WhenTextNotPresent() {
		Form form = new Form();
		form.param("sourcelang", "en-US");
		form.param("targetlang", "cs-CZ");
		Response response = target("/translate").request().post(Entity.form(form));
	 
	    assertEquals("Http Response should be 400 ", Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	    assertThat(response.readEntity(String.class), equalTo("Please provide text"));
	}
}

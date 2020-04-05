package com.app.translate;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;

import javax.ws.rs.core.Response;
import java.io.IOException;
import io.swagger.annotations.*;

import com.app.cache.InvocationCache;
import com.google.api.gax.rpc.InvalidArgumentException;

@Path("translate")
@Api(value = "Translate")
public class TranslateService {
	Config conf = new Config("config.properties");
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Get text translation",
		notes = "Translate text from source language to target language.",
		responseContainer = "String")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Trasnlated text"),
			@ApiResponse(code = 400, message = "Error message")
	})
	public Response convertText(
			@ApiParam(value = "Source Language e.g. en-US", required = true) @FormParam("sourcelang") String sourcelang, 
			@ApiParam(value = "Traget Language e.g. cs-CZ", required = true)@FormParam("targetlang") String targetlang,
			@ApiParam(value = "Text for translation", required = true) @FormParam("text") String text) throws IOException {
		
		String projectId = conf.getProperty("translate.project.id");
		Boolean error = false;
		String message = "";
		
		if (sourcelang == null) {
			error = true;
			message = "Please provide source language";
		} else if(targetlang == null) {
			error = true;
			message = "Please provide target language";
		} else if(text == null) {
			error = true;
			message = "Please provide text";
		}
		
		if (error) return Response.status(400).entity(message).build();
		
		String key = sourcelang + targetlang + text;
		String cachedValue = InvocationCache.getCache(key);
		
		if (cachedValue != null) return Response.ok(cachedValue).build();
		
		TranslateResponse resp = translateText(projectId, sourcelang, targetlang, text, key);
		return Response.status(resp.getStatusCode()).entity(resp.getText()).build();
	}
	

	// Translating Text
	private TranslateResponse translateText(String projectId, String sourcelang, String targetlang, String text, String key) throws IOException {
		TranslateResponse resp = new TranslateResponse();
		   
	   try (TranslationServiceClient client = TranslationServiceClient.create()) {
			LocationName parent = LocationName.of(projectId, "global");
			// Supported Mime Types:
			TranslateTextRequest request = TranslateTextRequest.newBuilder().setParent(parent.toString())
					.setMimeType("text/plain").setSourceLanguageCode(sourcelang).setTargetLanguageCode(targetlang)
					.addContents(text).build();

			TranslateTextResponse response = client.translateText(request);
			
			// Display the translation for each input text provided
			for (Translation translation : response.getTranslationsList()) {
				System.out.printf("Translated text v3: %s\n", translation.getTranslatedText());
				String result = translation.getTranslatedText();
				InvocationCache.setCache(key, result);
				resp.setValue(200, result);
				return resp;
			}
		} catch (InvalidArgumentException e) {
			System.out.printf(e.getMessage());
			resp.setValue(400, e.getMessage().toString());
			return resp;
		}
	   resp.setValue(500, "Something went Wrong");
	   return resp;
	}
}


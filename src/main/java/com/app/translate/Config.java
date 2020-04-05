package com.app.translate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	String fileName = "";
	String result = "";
	InputStream inputStream;
	
	public Config(String fileName) {
		this.fileName = fileName;
	}
	
	public String getProperty(String propertyName) throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(this.fileName);
	
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + this.fileName + "' not found in the classpath");
			}		
			result = prop.getProperty(propertyName);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}

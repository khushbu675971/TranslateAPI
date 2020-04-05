package com.app.swagger;

import io.swagger.jaxrs.config.BeanConfig;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.app.translate.Config;

public class SwaggerConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Config conf = new Config("config.properties");
	String host;
    String path;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
			host = conf.getProperty("base.host");
			path = conf.getProperty("base.path");
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
        
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost(host);
        beanConfig.setBasePath(path);
        beanConfig.setResourcePackage("com.app.translate");
        beanConfig.setScan(true);
    }
}
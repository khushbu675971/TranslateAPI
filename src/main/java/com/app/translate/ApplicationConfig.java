package com.app.translate;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
    	Set<Class<?>> resources = new HashSet<Class<?>>();
    	addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
    	resources.add(com.app.translate.TranslateService.class);
    }
}
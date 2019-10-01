package org.fastgrow.teadms.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ApplicationInitializer implements WebApplicationInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	LOGGER.trace("starting up...");

    	// Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfiguration.class);
        
        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));
        initJersey(servletContext);
    }

    private void initJersey(ServletContext servletContext) {
        JerseyInitializer.servletContext = servletContext;
        ServletRegistration.Dynamic reg = servletContext.addServlet("jersey", ServletContainer.class);
        reg.addMapping("/api/*");
        reg.setInitParameter("javax.ws.rs.Application", JerseyInitializer.class.getName());
    }
}

package org.fastgrow.teadms.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.fastgrow.teadms.api.endpoint.OrderEndpoint;
import org.fastgrow.teadms.api.endpoint.ProductEndpoint;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JerseyInitializer extends ResourceConfig {
    static ServletContext servletContext;

    public JerseyInitializer() {
    	// Logging
    	register(new LoggingFeature(
    			Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), 
    			Level.INFO, 
    			LoggingFeature.Verbosity.PAYLOAD_ANY, 1024));
        // Tracing support.
    	property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());

        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        register(context.getBean(ProductEndpoint.class));
        register(context.getBean(OrderEndpoint.class));
    }
}

package org.fri.entice.webapp.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * An example JAX-RS application using Apache Shiro.
 */
public class ExampleApplication extends ResourceConfig {

    public ExampleApplication() {
        super();
//        register(new AuthorizationFilterFeature());
//        register(new SubjectFactory());
//        register(new AuthInjectionBinder());
//        register(new ShiroExceptionMapper());

        register(MultiPartFeature.class);

        // register your REST service
        register(new JSONService());

        register(new SubjectAuthResource());
    }
}

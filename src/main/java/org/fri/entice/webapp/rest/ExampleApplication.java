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

        //String certificatesTrustStorePath = "/usr/lib/jvm/java-8-oracle/jre/lib/security/cacerts";
        //System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);


        JSONService jsonService = new JSONService();
        // register your REST service
        register(new JSONService());
        register(new GUIService(jsonService));
        register(new SZTAKIService(jsonService));



        register(new SubjectAuthResource());
    }
}

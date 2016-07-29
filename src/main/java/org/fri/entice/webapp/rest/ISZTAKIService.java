package org.fri.entice.webapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

public interface ISZTAKIService {
    @POST
    public String executeOptimizer(String imageURL );

    @GET
    public String getStatus(String optimizerID);

    @GET
    public String stopOptimization(String optimizerID);

    @GET
    public String cancelOptimization(String optimizerID);
}

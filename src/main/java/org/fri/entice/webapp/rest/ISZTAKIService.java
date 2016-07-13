package org.fri.entice.webapp.rest;

import javax.ws.rs.GET;

public interface ISZTAKIService {
    @GET
    public String executeOptimizer(String methodType);

    @GET
    public String getStatus(String optimizerID);

    @GET
    public String stopOptimization(String optimizerID);

    @GET
    public String cancelOptimization(String optimizerID);
}

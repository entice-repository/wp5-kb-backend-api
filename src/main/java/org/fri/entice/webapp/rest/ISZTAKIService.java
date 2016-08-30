package org.fri.entice.webapp.rest;

import org.fri.entice.webapp.entry.client.MyJsonObject;
import org.fri.entice.webapp.entry.client.SZTAKIExecuteObj;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

public interface ISZTAKIService {
    @POST
    public String executeOptimizer(SZTAKIExecuteObj sztakiExecuteObj);

    @GET
    public String getStatus(String optimizerID);

    @GET
    public String stopOptimization(String optimizerID);

    @GET
    public String cancelOptimization(String optimizerID);

    @POST
    public String executeImageBuild(MyJsonObject jsonObject);

    // get status and result
    @GET
    public String getImageBuilderStatus(String builderID, boolean showResult);

    // aka delete
    @GET
    public String stopImageBuilder(String builderID);
}

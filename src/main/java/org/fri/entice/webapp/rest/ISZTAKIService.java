package org.fri.entice.webapp.rest;

import org.fri.entice.webapp.entry.Quality;
import org.fri.entice.webapp.entry.RecipeBuild;
import org.fri.entice.webapp.entry.client.MyJsonObject;
import org.fri.entice.webapp.entry.client.SZTAKIExecuteObj;
import org.fri.entice.webapp.entry.client.SZTAKIOptimizationStatusObj;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.List;
import java.util.Map;

public interface ISZTAKIService {
    @POST
    public Map<String, String> executeOptimizer(SZTAKIExecuteObj sztakiExecuteObj);

    @GET
    public String deleteOptimizationJob(String entityID);

    @GET
    public String getStatus(String optimizerID);

    @GET
    public String getOptimizationStatusList(String optimizerID);

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
    public Map<String,String> stopImageBuilder(String builderID, boolean cancelExecution);

    @GET
    public String showLast10ValuesofOptimization(String optimizerID);

    @GET
    public List<RecipeBuild> getRecipeBuilds();

    @GET
    public List<Quality> getOptimizationList();

    @GET
    public List<SZTAKIOptimizationStatusObj> getOptimizationRefreshedList();

    @GET
    public List<RecipeBuild> getRecipeRefreshedBuilds();
}

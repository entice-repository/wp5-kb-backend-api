package org.fri.entice.webapp.client;

//import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import java.net.URI;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import org.fri.entice.webapp.entry.DiskImageResource;
//import org.fri.entice.webapp.entry.ImageType;
//import org.glassfish.jersey.client.ClientConfig;
//import javax.ws.rs.core.UriBuilder;
//import java.util.List;

public class CCGMiniClient {

    /**
     * TODO: update client calls
     */
    public static void main(String[] args) {

		String NS = "http://localhost:3030/test";

		//CREATING MODEL EXAMPLE

        Model m = ModelFactory.createDefaultModel();

        Resource r = m.createResource(NS + "r");
        Property p = m.createProperty(NS + "p");

        r.addProperty(p, "hello world", XSDDatatype.XSDstring);
        m.write(System.out, "Turtle");

		/*
		// Build a trivial example data set
		Model rdfsExample = ModelFactory.createDefaultModel();
		Property p = rdfsExample.createProperty(NS, "p");
		Property q = rdfsExample.createProperty(NS, "q");
		rdfsExample.add(p, RDFS.subPropertyOf, q);
		rdfsExample.createResource(NS+"a").addProperty(p, "foo");

		Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
		InfModel inf = ModelFactory.createInfModel(reasoner, rdfsExample);
		*/

//        ClientConfig config = new ClientConfig().register(JacksonFeatures.class);
//        Client client = ClientBuilder.newClient(config);
//        WebTarget service = client.target(getBaseURI());

		/*
        //------------------------------CREATE JSON DATA REPRESENTING THE VMimage OBJECT AND UPLOAD IT TO
		SERVER----------------------------------------------------------------------
		System.out.println("----------------------------------INSERTING A NEW DISK IMAGE TO
		DATABASE------------------------------------------------");
		*/
//        DiskImage img = new DiskImage(ImageType.CI, "the description of image 9", "the title of image 9",
// "predecessor fo image 9", FileFormat.FVD, "http://urlOfPictureOfImage9", false, "http://iriOfImage9",
//				155, 7300, 36, 26, 77, 28, false, 6625553, true);
//        Response response = service.path("mini").path("images").path("uploadImage").request().post(Entity.json(img),
//				Response.class);
//        String responseAsString = "";    //TODO: fix compile of commented code above!
//        //String responseAsString = response.readEntity(String.class);
//        System.out.println(responseAsString);
//        System.out.println("-------------------------------------------------------------\n\n\n");

        // Return code should be 200
        //System.out.println(response.getStatus());
        //System.out.println(response.getLocation());
        //------------------------------------------------------------------------------------------------------------------------------------------


		/*
        //+++++++++++++++++++++++++++++SPECIFY A LIST OF IDs, SEND IT AND RETRIEVE A LIST OF IMAGES WITH THOSE
		IDs+++++++++++++++++++++++++++++++++++
		//you give an array of IDs in JSON format and get back a list of images with those IDs also in JSON format
		System.out.println("++++++++++++++++++++++++++++++++++++++RETRIEVING THE IMAGES WITH GIVEN
		IDs++++++++++++++++++++++++++++++++++++++++++++");
		ArrayList<String> imgIds=new ArrayList<String>();
		imgIds.add("9aa71d11-00e8-45cc-9c36-6c7a9c69364c");
		imgIds.add("52e0761b-1cb4-4954-ab70-b23adf86418f");
		imgIds.add("9661598c-2748-42b3-84ce-842f5d5544f3");
		Response resp = service.path("mini").path("images").path("returnSpecifiedImages").request().post(Entity.json
		(imgIds),Response.class);
		String respAsString = resp.readEntity(String.class);
		System.out.println(respAsString);
		System.out.println
		("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n\n");
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		*/


		/*NOT WORKING CURRENTLY!!!!!!!!!
        //...............................SEND A LIST OF KEYWORDS AND GET BACK SEARCH
		RESULTS..............................................................
		ArrayList<String> searchedKeywords=new ArrayList<String>();
		searchedKeywords.add("keyword1");
		searchedKeywords.add("keyword2");
		Response resp = service.path("mini").path("images").path("returnSearchedImages").request().post(Entity.json
		(searchedKeywords),Response.class);
		String respAsString = resp.readEntity(String.class);
		System.out.println(respAsString);
		//................................................................................................................................................
		 */


//        /**
//         * RETRIEVING ALL THE IMAGES WITH GIVEN TYPE
//         */
////        String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
//        Client client = Client.create();
//        WebResource webResource = client.resource("http://localhost:8080/JerseyREST/rest/service" +
//				"/returnImagesWithGivenType");
////        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
//        ClientResponse jsonResponse = webResource.type("application/json").post(ClientResponse.class, ImageType.CI);
//        List<DiskImageResource> entitiesFromResponse = jsonResponse.getEntity(List.class);

        // 200 == OK
        // 201 == Created
//        if (jsonResponse.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : " + jsonResponse.getStatus());
//        }

        System.out.println("Output from Server .... \n");
//        String output = (String) jsonResponse.getEntity();
//        System.out.println(output);

        //#################################SPECIFY IMAGE TYPE AND GET BACK ALL THE IMAGES WITH GIVEN TYPE
//        System.out.println("#####################################RETRIEVING ALL THE IMAGES WITH GIVEN TYPE ");
//        Response respon = service.path("mini").path("images").path("returnImagesWithGivenType").request().post(Entity
//				.json(ImageType.CI), Response.class);
//        String responAsString = respon.readEntity(String.class);
//        System.out.println(responAsString);
//        System.out.println("#####################################################\n\n\n");
        //######################################################################################################################################


		/*
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$GET BACK ALL THE VMIMAGES IN THE
		DATABASE$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$RETRIEVING ALL THE DISK IMAGES IN THE
		DATABASE$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println(service.path("mini").path("images").request().accept(MediaType.APPLICATION_JSON).get(String
		.class));
		System.out.println
		("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n\n\n");
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		*/


        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%DELETE IMAGES WITH IDS SPECIFIED IN A GIVEN
        // LIST%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        //you give an array of IDs in JSON format and get back a number of images that were sucesfully deleted
//        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%DELETING THE IMAGES WITH GIVEN " +
//				"IDS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        ArrayList<String> imgIdsToDelete = new ArrayList<String>();
//        imgIdsToDelete.add("f0863189-e304-4540-9acd-45fe4ecd15fe");
//        imgIdsToDelete.add("9661598c-2748-42b3-84ce-842f5d5544f3");
//        imgIdsToDelete.add("52e0761b-1cb4-4954-ab70-b23adf86418f");
//        Response respi = service.path("mini").path("images").path("deleteSpecifiedImages").request().post(Entity.json
//				(imgIdsToDelete), Response.class);
//        String respiAsString = "";    //TODO: fix compile of commented code above!
////        String respiAsString = respi.readEntity(String.class);
//        System.out.println(respiAsString);
//        System.out.println
//
// ("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n\n\n");
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    }

    private static URI getBaseURI() {
        //return UriBuilder.fromUri("http://localhost:8080/CcgMiniV01").build();
        //return UriBuilder.fromUri("http://194.249.0.47:8080/CcgMiniV01").build();
        //return UriBuilder.fromUri("http://localhost:9999/CcgMiniV01").build();//used for TCP/IP monitor in Eclipse -
        // the monitor should be configured to forward packets to final server
		return null;
    }

}

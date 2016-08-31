package com.vmrepository.RepoGuardClientFunctionalities;

import com.vmrepository.repoguardwebserver.*;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOMFeature;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * <<< A Web-method to facilitate upload of optimized images into S3 repository by removing the unoptimized image from S3 repository>>>
 * <<< Returns a list : with storage node ID, VM-Image name, Bucket name and URI corresponding to uploaded VMI.>>>
 * @author : nishant.dps.uibk.ac.at
 */

public class UploadOptimizedVMI {

	public static final String WS_URL = "https://goedis.dps.uibk.ac.at:7070/WebServicesRepo/add?wsdl";

	public static void main(String[] args)
			throws MalformedURLException, IOException, FileNotFoundException, IOException, FileNotFoundException_Exception, IOException_Exception, URISyntaxException_Exception{

		AddVMIImplementationService client = new AddVMIImplementationService();
		AddVMIImplementation service = client.getAddVMIImplementationPort(new MTOMFeature(10240));

		BindingProvider provider = (BindingProvider) service;
		Map<String, Object> req_ctx = provider.getRequestContext();

		req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

		Map<String, List<String>> headers = new HashMap<String, List<String>>();

        /*
         * authenticate(user-name,password) checks for authentication on the
         * client side.
         */

		if (service.authenticate("FLEXIANT", "cVgZzW9Y&*xk%U=9")) {

            /*
             * Provide your user name and password as a header to enable the
             * server to upload the file
             */
			headers.put("Username", Collections.singletonList("FLEXIANT"));
			headers.put("Password", Collections.singletonList("cVgZzW9Y&*xk%U=9"));

			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            /*
             * vmImagename : name of VMI to be uploaded - Should be same name as unoptimized version.
             * vmImageSourcePath : Path to VMI
             */
			String vmImageNameOld = "Fedora-Workstation-netinst-x86_64-24-1.2.iso";
			String vmImageNameNew = "Fedora-Workstation3-netinst-x86_64-24-1.2.iso";

			String  url = "https://s3.amazonaws.com/flexiant-entice/Fedora-Workstation-netinst-x86_64-24-1.2.iso";

            /*
             * nodeIdOld - the storage where the Unoptimized VMI was stored : for the deletion of Unoptimized VMI
             * nodeIdNew - the storage location at which optimized VMI has to be stored.
             */

			String nodeIdOld = "3";
			String nodeIdNew = "2";

			List list = null;//service.receiveOptimizedVMImage(url, vmImageNameOld, vmImageNameNew, nodeIdOld, nodeIdNew);


			System.out.println(list);


			System.out.println("The VM Image: " + vmImageNameNew
					+ " has finished uploading. Thanks for being PATIENT! We hope to see you again for future UPLOAD.");

		} else {

			System.out.println("Check your authetication credentials");
		}

	}

} 
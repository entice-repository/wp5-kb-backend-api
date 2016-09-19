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

	public static final String WS_URL = "***";

	public static void main(String[] args)
			throws MalformedURLException, IOException, FileNotFoundException, IOException, FileNotFoundException_Exception, IOException_Exception, URISyntaxException_Exception{
		executeUpload("***",1);
	}

	public static String executeUpload(String url, int nodeIdNew)
			throws MalformedURLException, IOException, FileNotFoundException, IOException, FileNotFoundException_Exception, IOException_Exception, URISyntaxException_Exception{
		nodeIdNew = nodeIdNew + 1;

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

		if (service.authenticate("***", "***")) {

            /*
             * Provide your user name and password as a header to enable the
             * server to upload the file
             */
			headers.put("Username", Collections.singletonList("***"));
			headers.put("Password", Collections.singletonList("***"));

			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            /*
             * vmImagename : name of VMI to be uploaded - Should be same name as unoptimized version.
             * vmImageSourcePath : Path to VMI
             */
//			String vmImageNameNew = "Fedora-Workstation3-netinst-x86_64-24-1.2.iso";

            /*
             * nodeIdOld - the storage where the Unoptimized VMI was stored : for the deletion of Unoptimized VMI
             * nodeIdNew - the storage location at which optimized VMI has to be stored.
             */

			// https://entice.lpds.sztaki.hu:5443/api/imagebuilder/build/99b5f56b-75ba-4a02-ad37-52ecfbeb1afa/result/image
			long startTime = System.currentTimeMillis();
			StringBuffer sb = new StringBuffer();
			sb.append("Before upload starts.. " + startTime +  "ms");
			sb.append("\n");
//			System.out.println();
			final long currTime = System.currentTimeMillis();
			List list = service.receiveOptimizedVMImage(url, "anything", "image_"+currTime, "1", nodeIdNew + "");
			long endTime = System.currentTimeMillis();
//			System.out.println("Upload from URL finished" + (endTime - startTime) +  "ms");
			sb.append("Upload from URL finished" + (endTime - startTime) +  "ms");
			sb.append("\n");
//			System.out.println(list);
			sb.append(list);
			System.out.println("The VM Image: " + "image"
					+ " has finished uploading. Thanks for being PATIENT! We hope to see you again for future UPLOAD.");

			return list.toString();
//			return sb.toString();
		} else {
			System.out.println("Check your authetication credentials");
			return "Check your authetication credentials";
		}

	}
} 
package com.vmrepository.RepoGuardClientFunctionalities;

/* 
* ===================================================================================================
* This file is part of:  Entice Repository Environment
* Release version: 0.2
* ===========================================================================================================
* Developer: Nishant Saurabh, University of Innsbruck, DIstributed and Parallel Systems,  Innsbruck, Austria.
* @author : nishant.dps.uibk.ac.at
* 
* The project leading to this application has received funding
* from the European Union's Horizon 2020 research and innovation
* programme under grant agreement No 644179.
*
* Copyright 2016 
* Contact: Vlado Stankovski (vlado.stankovski@fgg.uni-lj.si)
* =================================================================================
* Licensed under the Apache License, Version 2.0 (the "License");
* you must not use this file except in compliance with the License.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*
* For details see the terms of the License (see attached file: README).
* The License is also available at http://www.apache.org/licenses/LICENSE-2.0.txt.
* ================================================================================
*/


import com.vmrepository.repoguardwebserver.AddVMIImplementation;
import com.vmrepository.repoguardwebserver.AddVMIImplementationService;
import com.vmrepository.repoguardwebserver.FileNotFoundException_Exception;
import com.vmrepository.repoguardwebserver.IOException_Exception;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOMFeature;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * <<< A Web Method to upload an Image directly to image store of a cloud provider without storing it to S3 Repository>>>
 * <<< Returns a list of name of VMI registered to image store, VMI registration Id, Size of VMI on CLoud test-bed, Cloud Provider Id, CLoud Provider Zone>>> 
 * @author : nishant.dps.uibk.ac.at
 */

public class UploadImageAsInstanceDirectly {
	
	
	public static final String WS_URL = "***";

	public static void main(String[] args)
			throws MalformedURLException, IOException, FileNotFoundException, IOException, FileNotFoundException_Exception, IOException_Exception{

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
			 * vmImagename : name of VMI to be uploaded to Image store as an Instance
			 * vmImageSourcePath : Path to VMI
			 */
			
			String vmImageName = "Lamp.qcow2";
			String vmImageSourcePath = "/home/nishant/Downloads/VMImages/Flexiant/LAMP/" + vmImageName;
			File vmImage = new File(vmImageSourcePath);

			FileInputStream in = null;
			BufferedInputStream bin = null;
			System.out.println("The VMI upload can take a while. BE PATIENT !");
			
			System.out.println("The size of file:" + vmImage.length());

			try {
				in = new FileInputStream(vmImage);
				bin = new BufferedInputStream(in);

				/*
				 * Change the size of byte array as per the settings of JVM heap
				 * size
				 */
				byte[] vmImageBytes = new byte[1024 * 1024 * 32];
				int bytesRead;
				
				/*
				 * This service is applicable if : 
				 * VM Image of appropriate format is directly to be put in image store instead of storing it at S3 storage repositories.
				 * CloudInfrastructure : where it has to be instantiated. Currently only UIBKs OpenStack is used.
				 * Later : we will add Amazon and ULs OpenNebula 
				 */
				String cloudProvider = "OpenStack";

				while ((bytesRead = bin.read(vmImageBytes)) != -1) {				
					List list = service.uploadImageInstanceDirectly(vmImageBytes, vmImage.getName(), bytesRead, vmImage.length(), cloudProvider); 
					System.out.println(list);
					
				}

			} catch (IOException e) {
				System.err.println(e);
			} finally {

				if (bin != null) {

					in.close();
				}

			}

			System.out.println("The VM Image: " + vmImageName
					+ " has finished uploading to the Instance Store. Thanks for being PATIENT! We hope to see you again for future UPLOAD.");

		} else {

			System.out.println("Check your authetication credentials");
		}

	}

}

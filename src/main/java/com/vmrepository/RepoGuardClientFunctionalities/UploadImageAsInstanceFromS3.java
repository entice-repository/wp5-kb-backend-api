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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * <<< A Web Method to upload an Image to image store of a cloud provider from already stored VMI in S3 repository>>>
 * <<< Returns a list of name of VMI registered to image store, VMI registration Id, Size of VMI on CLoud test-bed,
 * Cloud Provider Id, CLoud Provider Zone>>>
 * @author : nishant.dps.uibk.ac.at
 */


public class UploadImageAsInstanceFromS3 {

    public static final String WS_URL = "***";

    public static void main(String[] args) {
        try {
            uploadImageAsInstanceFromS3("***", "***", "Lamp.qcow2", "OpenStack", "***");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException_Exception e) {
            e.printStackTrace();
        } catch (IOException_Exception e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /*
    * vmImagename : name of VMI to be uploaded as Instance into image store for already stored VMI within S3 image
    * repository.
    * mImageName, URL of stored VMI and cloudProvider is to be sent as arguments.
    */
    public static String uploadImageAsInstanceFromS3(String username, String password, String vmImageName, String
			cloudProvider, String url) throws IOException, FileNotFoundException_Exception, IOException_Exception,
			URISyntaxException {
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
        if (service.authenticate(username, password)) {

			/*
			 * Provide your user name and password as a header to enable the
			 * server to upload the file
			 */
            headers.put("Username", Collections.singletonList(username));
            headers.put("Password", Collections.singletonList(password));

            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);


            System.out.println("The VMI upload can take a while. BE PATIENT !");

            List list = service.uploadImageInstanceFromS3(vmImageName, url, cloudProvider);

            System.out.println(list);

            System.out.println("The VM Image: " + vmImageName + " has finished uploading to the Instance Store. " +
					"Thanks for being PATIENT! We hope to see you again for future UPLOAD.");

            return list.toString();
        }
        else {
            System.out.println("Check your authetication credentials");
            return "Check your authetication credentials";
        }
    }


}

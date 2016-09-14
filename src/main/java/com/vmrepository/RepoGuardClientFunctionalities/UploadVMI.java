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


import com.vmrepository.repoguardwebserver.*;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOMFeature;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * <<< A Web-method to facilitate upload of unoptimized images.>>>
 * <<< Returns a list : with storage node ID, VM-Image name, Bucket name and URI corresponding to uploaded VMI.>>>
 * @author : nishant.dps.uibk.ac.at
 */

public class UploadVMI {

    public static final String WS_URL = "https://goedis.dps.uibk.ac.at:7070/WebServicesRepo/add?wsdl";

    public static void main(String[] args) {
        try {
            String vmImageName = "mini-ubuntu_12_04.iso";
            performUpload("C:\\Users\\sandig\\Downloads\\" + vmImageName,1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException_Exception e) {
            e.printStackTrace();
        } catch (IOException_Exception e) {
            e.printStackTrace();
        }
    }

    public static String performUpload(String vmImageSourcePath, int nodeId) throws MalformedURLException, IOException, FileNotFoundException, IOException,
            FileNotFoundException_Exception, IOException_Exception {
        AddVMIImplementationService client = new AddVMIImplementationService();
        AddVMIImplementation service = client.getAddVMIImplementationPort(new MTOMFeature(10240));

        nodeId = nodeId + 1;

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
			 * vmImagename : name of VMI to be uploaded
			 * vmImageSourcePath : Path to VMI
			 */

            //String vmImageName = "mini-ubuntu_12_04.iso";
            //String vmImageSourcePath = "C:\\Users\\sandig\\Downloads\\" + vmImageName;
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
                node ID | Matching repository
                ------------------------------
                1       | SZTAKI
                2       | UIBK
                3       | Amazon - Virginia East
                4       | LJ
                */
                //String vmibucketName = "flexiant-entice";
                String message = null;
                while ((bytesRead = bin.read(vmImageBytes)) != -1) {
                    List list = service.receiveVMImage(vmImageBytes, vmImage.getName(), bytesRead, vmImage.length(),
                            nodeId + "");
                    System.out.println(list);
                    message = list.toString();

                }
               // System.out.println("message: "+message);
                return message;

            } catch (IOException e) {
                System.err.println(e);
                return null;
            } catch (URISyntaxException_Exception e) {
                e.printStackTrace();
            } finally {

                if (bin != null) {

                    in.close();
                }

            }

			/*
			System.out.println("The VM Image: " + vmImageName
					+ " has finished uploading. Thanks for being PATIENT! We hope to see you again for future UPLOAD
					.");
			*/
        }
        else {
            System.out.println("Check your authetication credentials");
            return null;
        }

        return null;
    }

}

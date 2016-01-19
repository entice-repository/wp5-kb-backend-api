package org.fri.entice.webapp.uibk.client;

import com.vmrepository.repoguardwebserver.AddVMIImplementation;
import com.vmrepository.repoguardwebserver.AddVMIImplementationService;
import com.vmrepository.repoguardwebserver.FileNotFoundException_Exception;
import com.vmrepository.repoguardwebserver.IOException_Exception;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOMFeature;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * On running this class the following VM parameter must be added:
 * -Djavax.net.ssl.trustStore="C:\Program Files\Java\jre1.8.0_66\lib\security\cacerts"
 */

public class RepoClient {

    public static final String WS_URL = "https://goedis.dps.uibk.ac.at:7070/WebServicesRepo/add?wsdl";

    public static void main(String[] args) throws MalformedURLException, IOException, FileNotFoundException_Exception, IOException_Exception {

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

        String username = "LJUBLJANA";
        String password = "hyp!w7cUNkd$rd_V";

        if (service.authenticate(username, password)) {

			/*
			 * Provide your user name and password as a header to enable the
			 * server to upload the file
			 */
            headers.put("Username", Collections.singletonList(username));
            headers.put("Password", Collections.singletonList(password));

            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

			/*
			 * vmImagename : name of VMI to be uploaded
			 * vmImageSourcePath : Path to VMI
			 */

            String vmImageName = "ubuntu-14.04.3-server-i386.iso";
            String vmImageSourcePath = "C:\\Users\\sandig\\Downloads\\" + vmImageName;
            File vmImage = new File(vmImageSourcePath);

            FileInputStream in = null;
            BufferedInputStream bin = null;
            System.out.println("The VMI upload can take a while. BE PATIENT !");
            double fileSize = vmImage.length()*1e-6;
            System.out.println("File size for upload is: "+ fileSize + " MB");

            try {
                in = new FileInputStream(vmImage);
                bin = new BufferedInputStream(in);

				/*
				 * Change the size of byte array as per the settings of JVM heap
				 * size
				 */
                byte[] vmImageBytes = new byte[1024 * 1024 * 32];
                int bytesRead;

                double readMB = 0;
                while ((bytesRead = bin.read(vmImageBytes)) != -1) {
                    service.receiveVMImage(vmImageBytes, vmImage.getName(), bytesRead);
                    readMB+=bytesRead*1e-6;
                    System.out.println("MB read: " + readMB + " | "+ (int)(readMB/fileSize*100) + "%");
                }

            } catch (IOException e) {
                System.err.println(e);
            } finally {

                if (bin != null) {

                    in.close();
                }

            }

            System.out.println("The VM Image: " + vmImageName + " has finished uploading. Thanks for being PATIENT! We hope to see you again for future UPLOAD.");

        }
        else {

            System.out.println("Check your authetication credentials");
        }

    }

}


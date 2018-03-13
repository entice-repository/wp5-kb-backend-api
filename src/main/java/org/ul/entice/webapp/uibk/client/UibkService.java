package org.ul.entice.webapp.uibk.client;

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

public class UibkService implements IUibkService {


    @Override
    public boolean addInterface(File vmImage, String wsURL) {
        try {
            AddVMIImplementationService client = new AddVMIImplementationService();
            AddVMIImplementation service = client.getAddVMIImplementationPort(new MTOMFeature(10240));

            BindingProvider provider = (BindingProvider) service;
            Map<String, Object> req_ctx = provider.getRequestContext();

            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsURL);
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

                FileInputStream in = null;
                BufferedInputStream bin = null;
                System.out.println("The VMI upload can take a while. BE PATIENT !");
                double fileSize = vmImage.length() * 1e-6;
                System.out.println("File size for upload is: " + fileSize + " MB");

                try {
                    in = new FileInputStream(vmImage);
                    bin = new BufferedInputStream(in);

				/*
				 * Change the size of byte array as per the settings of JVM heap
				 * size
				 */
                    byte[] vmImageBytes = new byte[1024 * 1024 * 32];
                    int bytesRead;

                    double readMb = 0;
                    while ((bytesRead = bin.read(vmImageBytes)) != -1) {
//                        service.receiveVMImage(vmImageBytes, vmImage.getName(), bytesRead);
                        readMb += bytesRead / (1024 * 1024);
                        System.out.println("MB read: " + readMb + " | " + (int) (readMb / fileSize * 100) + "%");
                    }
                    // todo: Final message on upload?!
                } catch (IOException e) {
                    System.err.println(e);
                } finally {

                    if (bin != null) {

                        in.close();
                    }

                }

                System.out.println("The VM Image: " + vmImage.getName() + " has finished uploading. Thanks for being " +
                        "PATIENT! We hope to see you again for future UPLOAD.");

            }
            else {

                System.out.println("Check your authetication credentials");
            }

        } catch (MalformedURLException e) {

        } catch (IOException_Exception e) {

        } catch (IOException e) {

        } catch (FileNotFoundException_Exception e) {

        }
        return false;
    }
}

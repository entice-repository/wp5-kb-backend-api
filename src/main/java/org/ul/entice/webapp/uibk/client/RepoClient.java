package org.ul.entice.webapp.uibk.client;

import java.io.File;

/**
 * On running this class the following VM parameter must be added:
 * -Djavax.net.ssl.trustStore="C:\Program Files\Java\jre1.8.0_66\lib\security\cacerts"
 */

public class RepoClient {

    public static void main(String[] args) {
        UibkService uibkService = new UibkService();

        String vmImageName = "ubuntu-14.04.3-server-i386.iso";
        String vmImageSourcePath = "C:\\Users\\sandig\\Downloads\\" + vmImageName;
        File vmImage = new File(vmImageSourcePath);

        uibkService.addInterface(vmImage,"https://goedis.dps.uibk.ac.at:7070/WebServicesRepo/add?wsdl");

    }

}



package com.vmrepository.repoguardwebserver;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AddVMIImplementation", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AddVMIImplementation {


    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg4
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "receiveVMImage", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReceiveVMImage")
    @ResponseWrapper(localName = "receiveVMImageResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReceiveVMImageResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveVMImageRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveVMImageResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveVMImage/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveVMImage/Fault/IOException")
    })
    public List<String> receiveVMImage(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        long arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod(operationName = "StorageNodes")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "StorageNodes", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.StorageNodes")
    @ResponseWrapper(localName = "StorageNodesResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.StorageNodesResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/StorageNodesRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/StorageNodesResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/StorageNodes/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/StorageNodes/Fault/IOException")
    })
    public List<String> storageNodes()
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     * @throws ParseException_Exception
     * @throws URISyntaxException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "reDistribution", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReDistribution")
    @ResponseWrapper(localName = "reDistributionResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReDistributionResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistributionRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistributionResponse", fault = {
        @FaultAction(className = ParseException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistribution/Fault/ParseException"),
        @FaultAction(className = URISyntaxException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistribution/Fault/URISyntaxException"),
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistribution/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/reDistribution/Fault/IOException")
    })
    public void reDistribution(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws FileNotFoundException_Exception, IOException_Exception, ParseException_Exception, URISyntaxException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     * @throws URISyntaxException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "trackLocation", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.TrackLocation")
    @ResponseWrapper(localName = "trackLocationResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.TrackLocationResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/trackLocationRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/trackLocationResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/trackLocation/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/trackLocation/Fault/IOException"),
        @FaultAction(className = URISyntaxException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/trackLocation/Fault/URISyntaxException")
    })
    public List<String> trackLocation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws FileNotFoundException_Exception, IOException_Exception, URISyntaxException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBucketList", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetBucketList")
    @ResponseWrapper(localName = "getBucketListResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetBucketListResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketListRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketListResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketList/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketList/Fault/IOException")
    })
    public List<String> getBucketList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBucketVMIList", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetBucketVMIList")
    @ResponseWrapper(localName = "getBucketVMIListResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetBucketVMIListResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketVMIListRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketVMIListResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketVMIList/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getBucketVMIList/Fault/IOException")
    })
    public List<String> getBucketVMIList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod(operationName = "Authenticate")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Authenticate", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.Authenticate")
    @ResponseWrapper(localName = "AuthenticateResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.AuthenticateResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/AuthenticateRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/AuthenticateResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/Authenticate/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/Authenticate/Fault/IOException")
    })
    public boolean authenticate(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "optDistribute", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.OptDistribute")
    @ResponseWrapper(localName = "optDistributeResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.OptDistributeResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/optDistributeRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/optDistributeResponse")
    public boolean optDistribute(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg5
     * @param arg4
     * @param arg1
     * @param arg0
     * @param arg6
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "receiveOptimizedVMImage", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReceiveOptimizedVMImage")
    @ResponseWrapper(localName = "receiveOptimizedVMImageResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.ReceiveOptimizedVMImageResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveOptimizedVMImageRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveOptimizedVMImageResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveOptimizedVMImage/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/receiveOptimizedVMImage/Fault/IOException")
    })
    public List<String> receiveOptimizedVMImage(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        long arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        String arg6)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg4
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "uploadImageInstanceDirectly", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadImageInstanceDirectly")
    @ResponseWrapper(localName = "uploadImageInstanceDirectlyResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadImageInstanceDirectlyResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceDirectlyRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceDirectlyResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceDirectly/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceDirectly/Fault/IOException")
    })
    public List<String> uploadImageInstanceDirectly(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        long arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws FileNotFoundException_Exception
     * @throws IOException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "uploadImageInstanceFromS3", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadImageInstanceFromS3")
    @ResponseWrapper(localName = "uploadImageInstanceFromS3Response", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadImageInstanceFromS3Response")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceFromS3Request", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceFromS3Response", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceFromS3/Fault/FileNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadImageInstanceFromS3/Fault/IOException")
    })
    public List<String> uploadImageInstanceFromS3(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2)
        throws FileNotFoundException_Exception, IOException_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "uploadVMIAsObjectStorage", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadVMIAsObjectStorage")
    @ResponseWrapper(localName = "uploadVMIAsObjectStorageResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadVMIAsObjectStorageResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadVMIAsObjectStorageRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadVMIAsObjectStorageResponse")
    public void uploadVMIAsObjectStorage(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "cleanVMI", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.CleanVMI")
    @ResponseWrapper(localName = "cleanVMIResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.CleanVMIResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/cleanVMIRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/cleanVMIResponse")
    public void cleanVMI(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "deleteUnoptimizedVMI", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.DeleteUnoptimizedVMI")
    @ResponseWrapper(localName = "deleteUnoptimizedVMIResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.DeleteUnoptimizedVMIResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/deleteUnoptimizedVMIRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/deleteUnoptimizedVMIResponse")
    public void deleteUnoptimizedVMI(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "downloadVMIViaURI", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.DownloadVMIViaURI")
    @ResponseWrapper(localName = "downloadVMIViaURIResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.DownloadVMIViaURIResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/downloadVMIViaURIRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/downloadVMIViaURIResponse")
    public void downloadVMIViaURI(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws UnknownHostException_Exception
     * @throws URISyntaxException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVMIIP", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetVMIIP")
    @ResponseWrapper(localName = "getVMIIPResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.GetVMIIPResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getVMIIPRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getVMIIPResponse", fault = {
        @FaultAction(className = UnknownHostException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getVMIIP/Fault/UnknownHostException"),
        @FaultAction(className = URISyntaxException_Exception.class, value = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/getVMIIP/Fault/URISyntaxException")
    })
    public String getVMIIP(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws URISyntaxException_Exception, UnknownHostException_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "upload", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.Upload")
    @ResponseWrapper(localName = "uploadResponse", targetNamespace = "http://RepoGuardWebServer.vmrepository.com/", className = "com.vmrepository.repoguardwebserver.UploadResponse")
    @Action(input = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadRequest", output = "http://RepoGuardWebServer.vmrepository.com/AddVMIImplementation/uploadResponse")
    public long upload(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

}


package com.vmrepository.repoguardwebserver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.vmrepository.repoguardwebserver package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IOException_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "IOException");
    private final static QName _FileNotFoundException_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "FileNotFoundException");
    private final static QName _ReceiveVMImage_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "receiveVMImage");
    private final static QName _UploadResponse_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "uploadResponse");
    private final static QName _AuthenticateResponse_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "AuthenticateResponse");
    private final static QName _ReceiveVMImageResponse_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "receiveVMImageResponse");
    private final static QName _Authenticate_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "Authenticate");
    private final static QName _Upload_QNAME = new QName("http://RepoGuardWebServer.vmrepository.com/", "upload");
    private final static QName _UploadArg0_QNAME = new QName("", "arg0");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vmrepository.repoguardwebserver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuthenticateResponse }
     * 
     */
    public AuthenticateResponse createAuthenticateResponse() {
        return new AuthenticateResponse();
    }

    /**
     * Create an instance of {@link Authenticate }
     * 
     */
    public Authenticate createAuthenticate() {
        return new Authenticate();
    }

    /**
     * Create an instance of {@link Upload }
     * 
     */
    public Upload createUpload() {
        return new Upload();
    }

    /**
     * Create an instance of {@link ReceiveVMImageResponse }
     * 
     */
    public ReceiveVMImageResponse createReceiveVMImageResponse() {
        return new ReceiveVMImageResponse();
    }

    /**
     * Create an instance of {@link FileNotFoundException }
     * 
     */
    public FileNotFoundException createFileNotFoundException() {
        return new FileNotFoundException();
    }

    /**
     * Create an instance of {@link ReceiveVMImage }
     * 
     */
    public ReceiveVMImage createReceiveVMImage() {
        return new ReceiveVMImage();
    }

    /**
     * Create an instance of {@link UploadResponse }
     * 
     */
    public UploadResponse createUploadResponse() {
        return new UploadResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "FileNotFoundException")
    public JAXBElement<FileNotFoundException> createFileNotFoundException(FileNotFoundException value) {
        return new JAXBElement<FileNotFoundException>(_FileNotFoundException_QNAME, FileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveVMImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "receiveVMImage")
    public JAXBElement<ReceiveVMImage> createReceiveVMImage(ReceiveVMImage value) {
        return new JAXBElement<ReceiveVMImage>(_ReceiveVMImage_QNAME, ReceiveVMImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "uploadResponse")
    public JAXBElement<UploadResponse> createUploadResponse(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UploadResponse_QNAME, UploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "AuthenticateResponse")
    public JAXBElement<AuthenticateResponse> createAuthenticateResponse(AuthenticateResponse value) {
        return new JAXBElement<AuthenticateResponse>(_AuthenticateResponse_QNAME, AuthenticateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveVMImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "receiveVMImageResponse")
    public JAXBElement<ReceiveVMImageResponse> createReceiveVMImageResponse(ReceiveVMImageResponse value) {
        return new JAXBElement<ReceiveVMImageResponse>(_ReceiveVMImageResponse_QNAME, ReceiveVMImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Authenticate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "Authenticate")
    public JAXBElement<Authenticate> createAuthenticate(Authenticate value) {
        return new JAXBElement<Authenticate>(_Authenticate_QNAME, Authenticate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Upload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RepoGuardWebServer.vmrepository.com/", name = "upload")
    public JAXBElement<Upload> createUpload(Upload value) {
        return new JAXBElement<Upload>(_Upload_QNAME, Upload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = Upload.class)
    public JAXBElement<byte[]> createUploadArg0(byte[] value) {
        return new JAXBElement<byte[]>(_UploadArg0_QNAME, byte[].class, Upload.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = ReceiveVMImage.class)
    public JAXBElement<byte[]> createReceiveVMImageArg0(byte[] value) {
        return new JAXBElement<byte[]>(_UploadArg0_QNAME, byte[].class, ReceiveVMImage.class, ((byte[]) value));
    }

}

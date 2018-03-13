package org.ul.entice.webapp.client;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.s3.S3ApiMetadata;
import org.jclouds.s3.blobstore.S3BlobStore;
import org.jclouds.s3.blobstore.S3BlobStoreContext;
import org.ul.entice.webapp.util.CommonUtils;

import java.util.Properties;

/**
 * FGG S3 Hello world!
 */
public class S3ULTest {
    public static void main(String[] args) {
        /**
         *  You need to import server's certificate into JKS.
         *
         *  1) Obtain .der certificate, which you can find in the same directory as this source file.
         *
         *  2) Make sure you find out the location of the correct keystore, usually under:
         *      %JAVA_HOME%\jre\lib\security\cacerts.
         *
         *  3) Into cmd, type the following:
         *      keytool.exe -import -alias fgg-cl00.fgg.uni-lj.si -keystore %JAVA_HOME%\jre\lib\security\cacerts -file fggcl00-cert.der
         *      keytool.exe -import -alias fgg-cl00.fgg.uni-lj.si -keystore "C:\Program Files\Java\jdk1.8.0_111\jre\lib\security\cacerts" -file fggcl00-cert.crt
         *
         *  ( fggcl00-cert.der can be found under the project path: \src\main\resources\certs\ )
         *
         *  Note that keytool.exe is under bin directory, the same where java.exe is.
         */

        Properties properties = new Properties();

        // Each partner has its own "s3.properties" property file
        CommonUtils.initProperties(properties, "s3.properties");

        final String accessKey = properties.getProperty("access.key");
        final String secretKey = properties.getProperty("secret.key");

        final String endpoint = properties.getProperty("endpoint");

        S3BlobStore s3BlobStore;
        S3BlobStoreContext context = null;

        try {
            context = ContextBuilder.newBuilder(new S3ApiMetadata()).endpoint(endpoint).credentials(accessKey,
                    secretKey).name("FGG S3") // not needed
                    .buildApi(S3BlobStoreContext.class);

            s3BlobStore = context.getBlobStore();

            // S3 Object storage has a notion of buckets and objects. Bucket is like a directory,
            // but cannot be nested. Object is like a file.
            // Note that Apache JClouds API renamed buckets to containers and objects to blobs.

            String bucket = "bucket1";

            // List all buckets of authenticated user.
            for (StorageMetadata bm : s3BlobStore.list()) {
                System.out.println(bm);
            }

            // Create a new bucket if not exists. We can pass null Location.
            if (!s3BlobStore.containerExists(bucket)) s3BlobStore.createContainerInLocation(null, bucket);


            // List all buckets of authenticated user. Now it should have at least one bucket.
            for (StorageMetadata bm : s3BlobStore.list()) {
                System.out.println(bm);
            }

            // Create object and put it into a bucket.
            String blobName = "hello";
            Blob blob = s3BlobStore.blobBuilder(blobName).payload("Hello World!") // there are several options for
                    // this, e.g. files, bytes.
                    .build();
            s3BlobStore.putBlob(bucket, blob);

            // Check if object exists.
            System.out.format("Does object %s exist in bucket %s? %b%n", blobName, bucket, s3BlobStore.blobExists
                    (bucket, blobName));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (context != null) context.close();
        }
    }
}

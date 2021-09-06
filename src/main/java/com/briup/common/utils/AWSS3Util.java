package com.briup.common.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: guoqing.chen01@hand-china.com 2021-09-06 16:50
 **/

@Component
public class AWSS3Util {
    private AmazonS3 s3Client = getS3();
    private static final String BUCKETNAME = "travel-shanxi-gqchen";
    public AmazonS3 getS3() {
        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials(
                        "AKIA4Y3BQB3S5MK3U44Z",
                        "OABhqyShfr/+0/xix2T1k4hrIx8ihtcCMY4ZCzjS"
                );
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();
        return s3;
    }

    /**
     * 检查该存储桶是否存在，不存在则创建
     */
    public void checkBucket() {
        if(!s3Client.doesBucketExist(BUCKETNAME)) {
            Bucket bucket = s3Client.createBucket(BUCKETNAME);
        }
    }

    public Object put(String uri, File file) {
        checkBucket();
        try {
            PutObjectResult result = s3Client.putObject(BUCKETNAME, uri, file);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public S3ObjectInputStream get(String uri) {
        checkBucket();
        try {
            S3Object s3object = s3Client.getObject(BUCKETNAME, uri);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(String uri) {
        checkBucket();
        try {
            s3Client.deleteObject(BUCKETNAME, uri);
            return "删除成功";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void readAndWrite(InputStream in, OutputStream out) throws IOException {
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b);
        }
        in.close();
        out.flush();
        out.close();
    }

}

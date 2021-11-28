package com.briup.common.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author: guoqing.chen01@hand-china.com 2021-11-19 17:09
 **/

@Component
public class TencentCosUtil {

    @Value("${tencent.cos.account.secret-id}")
    private String SECRETID;
    @Value("${tencent.cos.account.secret-key}")
    private String SECRETKEY;
    @Value("${tencent.cos.region}")
    private String REGION;
    @Value("${tencent.cos.bucket}")
    private String BUCKET;
    private Logger logger = LoggerFactory.getLogger(TencentCosUtil.class);

    private COSCredentials cosCredentials = null;
    private COSClient cosClient = null;
    private TransferManager transferManager = null;

    COSClient getCosClient() {

        cosCredentials = new BasicCOSCredentials(SECRETID, SECRETKEY);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cosCredentials, clientConfig);
        // 4 检查该项目所使用的存储桶是否存在，不存在则创建
        if (!cosClient.doesBucketExist(BUCKET)) {
            cosClient.createBucket(BUCKET);
        }
        ExecutorService executorService = new ThreadPoolExecutor(1, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        transferManager = new TransferManager(cosClient, executorService);
        return cosClient;
    }

    public String put(String uri, File file) {
        if (cosClient == null) {
            getCosClient();
        }
        try {
            Upload upload = transferManager.upload(BUCKET, uri, file);
            UploadResult uploadResult = upload.waitForUploadResult();
            return uploadResult.toString();
        } catch (Throwable tb) {
            logger.error("上传失败");
            tb.printStackTrace();
        } /*finally {
            // 关闭 TransferManger
            transferManager.shutdownNow();
        }*/
        return BUCKET+"/"+uri;
    }

    public FileInputStream get(String key) {
        if (cosClient == null) {
            getCosClient();
        }
        try {
            final File file = File.createTempFile(key,"txt");
            GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET+"-"+SECRETID,key);
            transferManager.download(getObjectRequest, file);
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        } catch (Throwable tb) {
            logger.error("下载失败");
            tb.printStackTrace();
        } /*finally {
            // 关闭 TransferManger
            transferManager.shutdownNow();
        }*/
        return null;
    }

    /**
     * 删除
     */
    public String delete(String key) {
        if (cosClient == null) {
            getCosClient();
        }
        // 指定要删除的 bucket 和路径
        try {
            cosClient.deleteObject(BUCKET,key);
            return "删除成功";
        } catch (Exception e) {
            logger.error(e.getMessage());
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

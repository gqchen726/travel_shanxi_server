package com.briup.common.utils;

//import com.tencentcloudapi.common.Credential;

public class SMSUtils {

    private static String SECRET_ID = "";

    private static String SECRETKEY = "";

    private static String SIGN_ID = "";

    private static String TEMPLATE_ID = "";

    public static void sendSMS() {
        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
         * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
         * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
         * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
         */
//        Credential cred = new Credential("secretId", "secretKey");


    }
}

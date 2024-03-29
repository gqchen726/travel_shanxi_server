package com.briup.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//@Component
public class EmailUtils {

    private Logger logger = LoggerFactory.getLogger(EmailUtils.class);
    @Value("${email.emailAccount}")
    private  String emailAccount;
    @Value("${email.emailAccessKey}")
    private  String emailAccessKey;
    @Value("${email.emailServer}")
    private  String emailServer;
    @Value("${email.emailAccount}")
    private  String port;


    public  boolean sendemail(final String account,final String subject,final String content) throws Exception {
         if (!checkAccount(account)){
             return false;
         }
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host",emailServer);

        properties.setProperty("mail.transport.protocol","smtp");

        properties.setProperty("mail.smtp.auth","true");

        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount, emailAccessKey);
            }
        });
        //开启debug模式
        session.setDebug(true);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect(emailServer,emailAccount,emailAccessKey);

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(emailAccount));

        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(account));

        //邮件标题
        mimeMessage.setSubject(subject);

        //邮件内容
        mimeMessage.setContent(content,"text/html;charset=UTF-8");

        //发送邮件
        logger.info("*************send email");
        logger.info("*********************** send to "+account);
        logger.info("*********************** send  "+content);
        logger.info("*********************** send  subject"+content);
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();
        return true;
    }

    private  boolean checkAccount(final String account){
        if (!StringUtils.isEmpty(account) && account.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
            return true;
        }
        return false;
    }

}
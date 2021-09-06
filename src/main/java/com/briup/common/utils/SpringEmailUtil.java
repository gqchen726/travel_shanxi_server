package com.briup.common.utils;

import com.briup.common.respose.SimpleRespose;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: guoqing.chen01@hand-china.com 2021-09-02 09:54
 **/
@Component
public class SpringEmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final static Logger logger = LoggerFactory.getLogger(SpringEmailUtil.class);

    /**
     * 发送邮件工具类
     * @param recipient 收件人
     * @param content 邮件内容
     * @param subject 邮件标题
     * @return
     */
    public boolean sendEmail(String recipient, String subject, String content) {
        if (!checkRecipient(recipient)){
            return false;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
        logger.info("邮件发送成功,from {} to {}",sender,recipient);
        return true;
    }

    public void sendRegisterEmailCheckCode(String recipient) {
        String content = "";
        String subject = "安全验证码";
        sendEmail(recipient,content,subject);
    }

    private  boolean checkRecipient(final String account){
        if (!StringUtils.isEmpty(account) && account.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
            return true;
        }
        return false;
    }
}

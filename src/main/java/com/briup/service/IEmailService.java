package com.briup.service;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: guoqing.chen01@hand-china.com 2021-11-24 14:59
 **/

public interface IEmailService {
    String getRandomString();
    Object sendEmail(String mobileNumber, String email);
}

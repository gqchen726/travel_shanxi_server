package com.briup.service;

import com.briup.bean.user.User;
import com.briup.bean.user.ex.ChangePassword;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author: guoqing.chen01@hand-china.com 2021-11-24 14:56
 **/

public interface IUserService {
    List<Optional<User>> queryUserForExample();
}

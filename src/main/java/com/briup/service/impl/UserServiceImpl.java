package com.briup.service.impl;

import com.briup.bean.user.User;
import com.briup.bean.user.ex.ChangePassword;
import com.briup.dao.UserDao;
import com.briup.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author: guoqing.chen01@hand-china.com 2021-11-24 15:00
 **/

public class UserServiceImpl implements IUserService {
    @Resource
    private UserDao userDao;
    @Override
    public List<Optional<User>> queryUserForExample() {

        return null;
    }
}

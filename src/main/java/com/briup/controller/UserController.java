package com.briup.controller;

import com.briup.bean.user.User;
import com.briup.common.respose.SimpleRespose;
import com.briup.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user){
        Optional<User> one = userDao.findById(user.getMobileNumber());
        if (one.isPresent()){
            if (one.get().getPassword().equals(user.getPassword())) {
                return new SimpleRespose(one.get(), "登录成功", "0");
            }
        }
        return new SimpleRespose(null,"用户名或密码错误","1");
    }

    @RequestMapping("/create")
    @ResponseBody
    public Object create(@RequestBody User user){
        Optional<User> byId = userDao.findById(user.getMobileNumber());
        if (byId.isPresent()){
            return new SimpleRespose(null,"创建失败，该手机号已被注册","1");
        }
        String registerCode = getRandomString();
        user.setRegisterCode(registerCode);
        return new SimpleRespose(userDao.save(user),"创建成功","0");
    }

    /**
     * 生成随机6位数邀请码
     * @return
     */
    private  String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        User user = new User();
        boolean stop = true;
        while (stop){
            sb.delete(0,sb.length());
            for(int i=0;i<6;i++){
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }
            user.setRegisterCode(sb.toString());
            Optional<User> one = userDao.findOne(Example.of(user));
            stop = one.isPresent() ? true : false;
        }
        return sb.toString();
    }

    @GetMapping("checkMobileNumber")
    @ResponseBody
    public Object checkMobileNumber(@RequestParam String mobileNumber){
        boolean b = false;
        Optional<User> byId = userDao.findById(mobileNumber);
        if (!byId.isPresent()){
            b  =  true;
        }
        return new SimpleRespose(b,null,"0");
    }
}

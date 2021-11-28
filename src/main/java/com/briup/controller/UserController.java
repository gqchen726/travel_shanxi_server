package com.briup.controller;

import com.briup.bean.user.User;
import com.briup.bean.user.ex.ChangePassword;
import com.briup.common.respose.SimpleRespose;
import com.briup.common.utils.EmailUtils;
import com.briup.common.utils.RedisUtil;
import com.briup.common.utils.SpringEmailUtil;
import com.briup.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SpringEmailUtil emailUtils;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user) {
        Optional<User> one = userDao.findById(user.getMobileNumber());
        String message = "登录成功";
        String code = "0";
        User resUser = one.isPresent() ? one.get() : null;
        if (!one.isPresent()) {
            message = "登录失败,该用户不存在";
            code = "1";
        } else {
            if (StringUtils.isEmpty(user.getPassword())) {
                if (!user.getCheckCode().equals(redisUtil.get(user.getMobileNumber()))) {
                    message = "登录失败,验证码错误";
                    code = "1";
                }
            } else {
                if (!one.get().getPassword().equals(user.getPassword())) {
                    message = "登录失败,用户名或密码错误";
                    code = "1";
                }
            }
        }
        return new SimpleRespose(resUser, message, code);

    }

    @RequestMapping("/register")
    @ResponseBody
    public Object register(@RequestBody User user) {
        Optional<User> op = userDao.findById(user.getMobileNumber());
        if (userDao.findById(user.getMobileNumber()).isPresent()) {
            return new SimpleRespose(null, "注册失败，该手机号已被注册", "1");
        }
        User tempUser = new User();
        tempUser.setEmail(user.getEmail());
        if (userDao.findOne(Example.of(tempUser)).isPresent()) {
            return new SimpleRespose(null, "注册失败，该邮箱已被注册", "1");
        }

        if (StringUtils.isEmpty(user.getCheckCode()) || !user.getCheckCode().equals(redisUtil.get(user.getMobileNumber()))) {
            return new SimpleRespose(null, "注册失败，验证码不正确", "1");
        }
        String registerCode = user.getRegisterCode();
        if (null != registerCode && !StringUtils.isEmpty(registerCode)){
            if (registerCode.equals("17060242")){
                user.setAdmin("true");
            }
        }
        return new SimpleRespose(userDao.save(user), "注册成功", "0");
    }

    /**
     * 生成随机6位验证码
     *
     * @return
     */
    private String getRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        User user = new User();
        boolean stop = true;
        while (stop) {
            sb.delete(0, sb.length());
            for (int i = 0; i < 6; i++) {
                int number = random.nextInt(62);
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
    public Object checkMobileNumber(@RequestParam String mobileNumber) {
        boolean b = false;
        Optional<User> byId = userDao.findById(mobileNumber);
        if (!byId.isPresent()) {
            b = true;
        }
        return new SimpleRespose(b, null, "0");
    }

    @PostMapping("update")
    @ResponseBody
    public Object update(@RequestBody User user) {
        Optional<User> findUser = userDao.findById(user.getMobileNumber());
        if (findUser.isPresent()) {
            User user1 = findUser.get();
            user1 = user;
            return new SimpleRespose(userDao.save(user1), "success", "0");
        } else {
            return new SimpleRespose(null, "该用户不存在", "1");
        }

    }

    @PostMapping("changePassword")
    @ResponseBody
    public Object changePassword(@RequestBody ChangePassword changePassword,HttpServletRequest request,HttpServletResponse response) {
        Optional<User> byId = userDao.findById(changePassword.getMobileNumber());
        if (byId.isPresent()) {
        if (!StringUtils.isEmpty(changePassword.getOldPassword()))   {
                if (byId.get().getPassword().equals(changePassword.getOldPassword())) {
                    if (byId.get().getPassword().equals(changePassword.getNewPassword())){
                        return new SimpleRespose(null,"新密码和旧密码不可一致","1");
                    }
                    byId.get().setPassword(changePassword.getNewPassword());
                    return new SimpleRespose(userDao.save(byId.get()), "修改成功", "0");
                } else {
                    return new SimpleRespose(null, "密码修改失败，请确认旧密码", "1");
                }
            }
            String code = redisUtil.get(changePassword.getMobileNumber()).toString();
            if (!StringUtils.isEmpty(code)){
                if (code.equals(changePassword.getCheckCode())){
                    byId.get().setPassword(changePassword.getNewPassword());
                    return new SimpleRespose(userDao.save(byId.get()), "修改成功", "0");
                }
            }
            return new SimpleRespose(null, "验证码错误，请重试", "1");
        }
        return null;
    }

    /**
     * 发送注册邮件验证码
     * @param mobileNumber
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping("sendEmail")
    @ResponseBody
    public Object sendEmail(@RequestParam(name = "mobileNumber",required = true) String mobileNumber, @RequestParam (name = "email",required = false) String email) throws Exception {
        //email为空时发送更改密码邮箱验证码
        //email不为空时发送注册邮箱验证码

        //生成6位随机验证码
        String randomString = getRandomString();
        //存入redis
        if (!StringUtils.isEmpty(randomString)) {
            redisUtil.put(mobileNumber,randomString);
        }
        String emailContent = null;
        String emailSubject = "账户操作验证";
        String emailAccount = email;
        if (emailAccount != null ){
            emailContent = "您正在进行注册操作，验证码为111111，如非本人操作，请忽略";
        } else {
            Optional<User> byId = userDao.findById(mobileNumber);
            if (byId.isPresent()){
                emailAccount = byId.get().getEmail();
                emailContent = "您正在通过邮件修改密码，您的验证码为 111111 ,如非本人操作，请忽略";

            }
        }
        emailContent = emailContent.replaceAll("111111",randomString);
        if(emailUtils.sendEmail(emailAccount, emailSubject, emailContent)){
            return new SimpleRespose(null,"邮件发送成功","0");
        } else {
            return new SimpleRespose(null,"邮件发送失败","1");
        }
    }

}

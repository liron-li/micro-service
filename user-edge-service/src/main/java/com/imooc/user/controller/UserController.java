package com.imooc.user.controller;

import com.imooc.thrift.user.UserInfo;
import com.imooc.user.response.Response;
import com.imooc.user.thrift.ServiceProvider;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestParam("username") String username, @RequestParam("password") String password,
                             @RequestParam("realname") String realname,
                             @RequestParam("mobile") String mobile,
                             @RequestParam("email") String email

    ) {
        UserInfo userInfo = new UserInfo();

        userInfo.setUsername(username);
        userInfo.setPassword(md5(password));
        userInfo.setRealname(realname);
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);

        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.REGISTER_ERROR;
        }

        return Response.SUCCESS;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password) {

        // 1. 验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USER_PASSWORD_INVALID;
        }

        if (userInfo == null) {
            return Response.USER_PASSWORD_INVALID;
        }

        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USER_PASSWORD_INVALID;
        }


        // 2. 生成token
        String token = genToken();

        // 3. 缓存用户

        return Response.USER_PASSWORD_INVALID;
    }

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

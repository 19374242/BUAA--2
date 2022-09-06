package com.example.backend.Controller;

import com.example.backend.Mapper.UserMapper;
import com.example.backend.Service.UserService;
import com.example.backend.entity.GlobalVariable;
import com.example.backend.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user")  //给接口加前缀，要在/user处访问才行
public class UserController {
    //注入该组件
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    //登录
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestParam String email,
                                         @RequestParam String password) {
        Integer status = 0;
        if (userMapper.isEmailOccupy(email) == null) status = 2;
        else if (!userMapper.emailForPassword(email).equals(password)) status = 1;
        String name = userMapper.emailForName(email);
        String nickname = userMapper.emailForNickname(email);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("status", status);
        hm.put("name", name);
        hm.put("nickname", nickname);
        hm.put("email", email);
        return hm;
    }

    //注册
    @PostMapping("/register")
    public Integer register(@RequestBody User user) {
        return userService.register(user);
    }

//发送验证码
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String email) throws MessagingException {
        GlobalVariable.emailCode = String.valueOf(new Random().nextInt(8999) + 1000);
        String sender = "super2021_41@163.com"; //发送方邮箱
        String receiver = email; //接收方邮箱
        String title = "来自墨书的验证码";
        String text = "您的验证码为" + GlobalVariable.emailCode + ",请尽快输入";
        if (userService.sendEmail(sender, receiver, title, text) == 0) {
            return GlobalVariable.emailCode;
        } else {
            return "-1";
        }
    }
//    修改密码
    @PostMapping("/changePassword")
    public Integer changePassword(@RequestParam String email,
                                 @RequestParam String newPassword,
                                 @RequestParam String checkCode){
        return userService.changePassword(email,newPassword,checkCode);
    }
}

package com.example.backend.Service;

import com.example.backend.Mapper.UserMapper;
import com.example.backend.entity.GlobalVariable;
import com.example.backend.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Slf4j
@Service  //意思是将其注入spring容器中
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender mailSender;

    public Integer register(User user) {
        String email = user.getEmail();
//        邮箱被占用
        if (userMapper.isEmailOccupy(email) != null) {
            return 1;
        }
//        验证码错误
        if(!user.getCheckout().equals(GlobalVariable.emailCode)){
            return 2;
        }
        userMapper.insertUser(user);
        return 0;
    }

    public Integer sendEmail(String sender, String receiver, String title, String text) throws MessagingException {
        if((!receiver.contains("@"))||(!receiver.contains("."))) return -1;
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //发送方
            helper.setFrom(sender);
            //接收方
            helper.setTo(receiver);
            //发送的标题
            helper.setSubject(title);
            //发送的内容
            helper.setText(text);
        } catch (Exception e) {
            return -1;
        }
        mailSender.send(message);
        return 0;
    }
//    修改密码
    public Integer changePassword(String email,String newPassword,String checkOut){
        if(!GlobalVariable.emailCode.equals(checkOut)) return 1;
        if(userMapper.isEmailOccupy(email)==null) return -1;
        userMapper.emailChangePassword(email,newPassword);
        return 0;
    }
}

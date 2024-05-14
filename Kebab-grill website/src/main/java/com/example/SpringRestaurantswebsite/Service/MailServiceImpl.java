package com.example.SpringRestaurantswebsite.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService{


    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public void send(String formAddress, String toAddress, String subject, String content) throws Exception {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper  = new MimeMessageHelper(mailMessage, true);
        mimeMessageHelper.setFrom(formAddress);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        javaMailSender.send(mailMessage);
        System.out.println("mail sent");


    }
}

package com.project.uber.uberApp.services.Impl;

import com.project.uber.uberApp.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try{

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email send successfully");
        }catch (Exception e){
            log.info("Cannot send email:"+e.getMessage());
        }
    }

    @Override
    public void sendEmail(String[] toEmail, String subject, String body) {
        try{

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email send successfully");
        }catch (Exception e){
            log.info("Cannot send email:"+e.getMessage());
        }
    }
}

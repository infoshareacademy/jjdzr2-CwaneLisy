package com.infoshare.mailService;

import com.infoshare.domain.NeedRequest;
import com.infoshare.service.EventType;
import com.infoshare.service.NeedRequestObserver;
import com.infoshare.service.NeedRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceConfig implements NeedRequestObserver {

    private JavaMailSender javaMailSender;
    private NeedRequestService needRequestService;

    @Autowired
    public MailServiceConfig(JavaMailSender javaMailSender, NeedRequestService needRequestService) {
        this.javaMailSender = javaMailSender;
        this.needRequestService = needRequestService;

        this.needRequestService.attach(this);
    }

    public void sendMail(String to, String subject, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom("Helpick! <helpick.application@gmail.com>");

        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void update(EventType eventType, NeedRequest needRequest) {
        sendMail("m24magda@wp.pl", "Test e-mail from helpick", needRequest.toString());
    }
}

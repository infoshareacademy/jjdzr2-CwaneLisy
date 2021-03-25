package com.infoshare.mailService;

import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.Volunteer;
import com.infoshare.service.EventType;
import com.infoshare.service.NeedRequestObserver;
import com.infoshare.service.NeedRequestService;
import com.infoshare.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService implements NeedRequestObserver {

    private static final String EMAIL_SUBJECT = "New person in need registered in your location!";
    private static final String EMAIL_SENDER = "Helpick! <helpick.application@gmail.com>";


    private final JavaMailSender javaMailSender;
    private final NeedRequestService needRequestService;
    private final VolunteerService volunteerService;

    @Autowired
    public MailService(JavaMailSender javaMailSender, NeedRequestService needRequestService, VolunteerService volunteerService) {
        this.javaMailSender = javaMailSender;
        this.needRequestService = needRequestService;
        this.volunteerService = volunteerService;
        this.needRequestService.attach(this);
    }

    public void sendMail(String recipient, String subject, String content){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setFrom(EMAIL_SENDER);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void update(EventType eventType, NeedRequest needRequest) {
        if (eventType.equals(EventType.add)) {
            List<Volunteer> volunteersToNotify = volunteerService.getVolunteerFilteredList(needRequest.getPersonInNeed().getLocation(), needRequest.getTypeOfHelp());
            for (Volunteer volunteer: volunteersToNotify) {
                System.out.println(volunteer.getEmail());
                sendMail(volunteer.getEmail(), EMAIL_SUBJECT, needRequest.toString());
            }
        }
    }

}

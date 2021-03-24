package com.infoshare.controller;

import com.infoshare.mailService.MailServiceConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private MailServiceConfig mailServiceConfig;


    public MailController(MailServiceConfig mailServiceConfig) {
        this.mailServiceConfig = mailServiceConfig;
    }

    @GetMapping("/sendmail")
    public String sendMail(){
        mailServiceConfig.sendMail("m24magda@wp.pl", "Test e-mail from helpick", "Testing mail functionality");

        return "E-mail send";
    }




}

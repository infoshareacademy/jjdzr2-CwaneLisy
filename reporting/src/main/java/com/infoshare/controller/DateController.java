package com.infoshare.controller;

import com.infoshare.DTO.HelloDTO;
import com.infoshare.DTO.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date")
public class DateController {

    @GetMapping
    public ResponseDTO receiveDataFromHelpick(HelloDTO helloDTO){
        return new ResponseDTO(helloDTO.getWelcomeMessage() + "MAGDA");
    }


}

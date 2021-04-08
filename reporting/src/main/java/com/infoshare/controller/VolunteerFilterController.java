package com.infoshare.controller;


import com.infoshare.DTO.HelloDTO;
import com.infoshare.DTO.ResponseDTO;
import com.infoshare.DTO.VolunteerSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
public class VolunteerFilterController {

//    private final ReportingService reportingService;
//
//    @Autowired
//    public VolunteerFilterController(ReportingService reportingService) {
//        this.reportingService = reportingService;
//    }


    @GetMapping
    public ResponseDTO receiveDataFromHelpick(HelloDTO helloDTO){
        return new ResponseDTO(helloDTO.getWelcomeMessage() + " MAGDA");
    }


    @PostMapping
    public String receiveData(@RequestBody VolunteerSearchDTO filterForm){
//        reportingService.add(filterForm);
        System.out.println(filterForm.getLocation());
        return "OK";
    }



}

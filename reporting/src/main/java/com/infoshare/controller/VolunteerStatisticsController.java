package com.infoshare.controller;


import com.infoshare.DTO.HelloDTO;
import com.infoshare.DTO.ResponseDTO;
import com.infoshare.DTO.VolunteerSearchDTO;
import com.infoshare.service.ReportingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteerSearchStats")
public class VolunteerStatisticsController {

    private final ReportingService reportingService;

    public VolunteerStatisticsController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }


    @GetMapping
    public ResponseDTO receiveDataFromHelpick(HelloDTO helloDTO){
        return new ResponseDTO(helloDTO.getWelcomeMessage() + " MAGDA");
    }


    @PostMapping
    public String receiveData(@RequestBody VolunteerSearchDTO filterForm){
        reportingService.add(filterForm);
        return "OK";
    }



}

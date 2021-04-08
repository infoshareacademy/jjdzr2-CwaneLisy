package com.infoshare.controller;


import com.infoshare.DTO.HelloDTO;
import com.infoshare.DTO.NeedRequestSearchDTO;
import com.infoshare.DTO.ResponseDTO;
import com.infoshare.DTO.VolunteerSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/needRequestSearchStats")
public class NeedRequestStatisticsController {

//    private final ReportingService reportingService;
//
//    @Autowired
//    public VolunteerFilterController(ReportingService reportingService) {
//        this.reportingService = reportingService;
//    }


    @PostMapping
    public String receiveData(@RequestBody NeedRequestSearchDTO searchDTO){
//        reportingService.add(filterForm);
        System.out.println(searchDTO.getLocation());
        return "OK";
    }



}

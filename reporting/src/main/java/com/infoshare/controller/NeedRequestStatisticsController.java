package com.infoshare.controller;


import com.infoshare.DTO.NeedRequestSearchDTO;
import com.infoshare.service.ReportingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/needRequestSearchStats")
public class NeedRequestStatisticsController {

    private final ReportingService reportingService;

    public NeedRequestStatisticsController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @PostMapping
    public String receiveData(@RequestBody NeedRequestSearchDTO searchDTO){
        reportingService.add(searchDTO);
        return "OK";
    }

}

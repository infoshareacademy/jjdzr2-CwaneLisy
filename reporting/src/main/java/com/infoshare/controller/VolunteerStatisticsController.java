package com.infoshare.controller;

import com.infoshare.DTO.*;
import com.infoshare.service.ReportingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/volunteerSearchStats")
public class VolunteerStatisticsController {

    private final ReportingService reportingService;

    public VolunteerStatisticsController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("totalCount/{year}/{month}/{day}")
    public SearchCountDTO getSearchCountByDate(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return reportingService.getVolunteerSearchCountByDate(LocalDate.of(year, month, day));
    }

    @PostMapping
    public String receiveData(@RequestBody VolunteerSearchDTO searchDTO) {
        reportingService.add(searchDTO);
        return "OK";
    }
}

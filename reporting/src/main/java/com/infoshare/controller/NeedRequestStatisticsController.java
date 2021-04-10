package com.infoshare.controller;

import com.infoshare.DTO.NeedRequestSearchDTO;
import com.infoshare.DTO.SearchCountDTO;
import com.infoshare.service.ReportingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/needRequestSearchStats")
public class NeedRequestStatisticsController {

    private final ReportingService reportingService;

    public NeedRequestStatisticsController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("totalCount/{year}/{month}/{day}")
    public SearchCountDTO getSearchCountByDate(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return reportingService.getNeedRequestSearchCountByDate(LocalDate.of(year, month, day));
    }

    @PostMapping
    public String receiveData(@RequestBody NeedRequestSearchDTO searchDTO) {
        reportingService.add(searchDTO);
        return "OK";
    }

}

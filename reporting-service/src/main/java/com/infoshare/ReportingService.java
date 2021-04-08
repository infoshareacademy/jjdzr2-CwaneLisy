package com.infoshare;


import com.infoshare.dto.VolunteerFilterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ReportingService {


    List<VolunteerFilterForm> filterData = new ArrayList<>();

    public void add(VolunteerFilterForm form){
        System.out.println("HElllo " + form.getLocation());
        filterData.add(form);
    }

}

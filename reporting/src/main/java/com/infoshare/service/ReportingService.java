package com.infoshare.service;

import com.infoshare.DTO.VolunteerSearchDTO;
import com.infoshare.functions.VolunteerFunctions;
import com.infoshare.repositories.VolunteerStatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReportingService {

    private final VolunteerStatsRepository volunteerStatsRepo;

    public ReportingService(VolunteerStatsRepository volunteerStatsRepo) {
        this.volunteerStatsRepo = volunteerStatsRepo;
    }

    public void add(VolunteerSearchDTO searchDTO){
        volunteerStatsRepo.save(VolunteerFunctions.searchDTOToStatistics.apply(searchDTO));
    }

}

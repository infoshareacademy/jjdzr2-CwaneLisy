package com.infoshare.service;

import com.infoshare.DTO.NeedRequestSearchDTO;
import com.infoshare.DTO.SearchCountDTO;
import com.infoshare.DTO.VolunteerSearchDTO;
import com.infoshare.functions.NeedRequestFunctions;
import com.infoshare.functions.VolunteerFunctions;
import com.infoshare.repositories.NeedRequestStatsRepository;
import com.infoshare.repositories.VolunteerStatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ReportingService {

    private final VolunteerStatsRepository volunteerStatsRepo;
    private final NeedRequestStatsRepository needRequestStatsRepo;

    public ReportingService(VolunteerStatsRepository volunteerStatsRepo, NeedRequestStatsRepository needRequestStatsRepo) {
        this.volunteerStatsRepo = volunteerStatsRepo;
        this.needRequestStatsRepo = needRequestStatsRepo;
    }

    public void add(VolunteerSearchDTO searchDTO) {
        volunteerStatsRepo.save(VolunteerFunctions.searchDTOToStatistics.apply(searchDTO));
    }

    public void add(NeedRequestSearchDTO searchDTO) {
        needRequestStatsRepo.save(NeedRequestFunctions.searchDTOToStatistics.apply(searchDTO));
    }

    public SearchCountDTO getVolunteerSearchCountByDate(LocalDate date) {
        return new SearchCountDTO(volunteerStatsRepo.getSearchCountByDate(date), date);
    }

    public SearchCountDTO getNeedRequestSearchCountByDate(LocalDate date) {
        return new SearchCountDTO(needRequestStatsRepo.getSearchCountByDate(date), date);
    }
}

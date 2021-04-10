package com.infoshare.functions;

import com.infoshare.DTO.VolunteerSearchDTO;
import com.infoshare.domain.VolunteerStatistics;

import java.util.function.Function;

public class VolunteerFunctions {

    public static final Function<VolunteerSearchDTO, VolunteerStatistics> searchDTOToStatistics = VolunteerSearchDTO -> new VolunteerStatistics(
            VolunteerSearchDTO.getFreeText(),
            VolunteerSearchDTO.getLocation(),
            VolunteerSearchDTO.getSearchDate()
    );

    public static final Function<VolunteerStatistics, VolunteerSearchDTO> statisticsToSearchDTO = VolunteerStatistics -> new VolunteerSearchDTO(
            VolunteerStatistics.getFreeText(),
            VolunteerStatistics.getLocation(),
            null,
            VolunteerStatistics.getSearchDate()
    );
}

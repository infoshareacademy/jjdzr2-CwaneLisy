package com.infoshare.functions;

import com.infoshare.DTO.NeedRequestSearchDTO;
import com.infoshare.domain.NeedRequestStatistics;

import java.util.function.Function;

public class NeedRequestFunctions {

    public static final Function<NeedRequestSearchDTO, NeedRequestStatistics> searchDTOToStatistics = NeedRequestSearchDTO -> new NeedRequestStatistics(
            NeedRequestSearchDTO.getFreeText(),
            NeedRequestSearchDTO.getLocation(),
            NeedRequestSearchDTO.getSearchDate()
    );
}

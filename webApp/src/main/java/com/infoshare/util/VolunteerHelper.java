package com.infoshare.util;

import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.dto.NeedRequestFilterForm;
import com.infoshare.dto.VolunteerFilterForm;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VolunteerHelper {

    public static final String FILTER_TYPE_OF_HELPS = "filterTypeOfHelps";
    public static final String FILTER_FREE_TEXT = "filterFreeText";
    public static final String FILTER_LOCATION = "filterLocation";
    public static final String FILTER_IS_AVAILABLE = "filterIsAvailable";

    public static Map<String, Object> createFilteringRedirectAttributes(VolunteerFilterForm form) {
        return Map.of(
                FILTER_FREE_TEXT, form.getFreeText(),
                FILTER_TYPE_OF_HELPS, form.getTypeOfHelps(),
                FILTER_IS_AVAILABLE,form.isAvailable(),
                FILTER_LOCATION, form.getLocation());

    }
    public static VolunteerFilterForm addFilteringForm(Map<String, String> requestValues) {

        Set<TypeOfHelp> typeOfHelp = requestValues.get(FILTER_TYPE_OF_HELPS) != null ?
                Arrays.stream(requestValues.get(FILTER_TYPE_OF_HELPS).split(","))
                        .filter(s -> !s.isEmpty())
                        .map(TypeOfHelp::valueOf).collect(Collectors.toSet()) : null;


        return VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withFreeText(requestValues.get(FILTER_FREE_TEXT))
                .withLocation(requestValues.get(FILTER_LOCATION))
                .withIsAvailable(Boolean.valueOf(requestValues.get(FILTER_IS_AVAILABLE)))
                .withTypeOfHelps(typeOfHelp)
                               .build();
    }
    public static Map<String, String> filterAttributes(Map<String, String> requestValues) {
        return requestValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("filter"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

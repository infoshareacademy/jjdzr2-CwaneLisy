package com.infoshare.util;

import com.infoshare.dto.VolunteerFilterForm;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
@Service
public class VolunteerFilteringService {

    public static final String FILTER_TYPE_OF_HELPS = "filterTypeOfHelps";
    public static final String FILTER_FREE_TEXT = "filterFreeText";
    public static final String FILTER_LOCATION = "filterLocation";
    public static final String FILTER_ONLY_AVAILABLE = "filterOnlyAvailable";

    public Map<String, Object> createFilteringRedirectAttributes(VolunteerFilterForm form) {
        return Map.of(
                FILTER_FREE_TEXT, form.getFreeText(),
                FILTER_TYPE_OF_HELPS, form.getTypeOfHelps(),
                FILTER_ONLY_AVAILABLE, form.isAvailable(),
                FILTER_LOCATION, form.getLocation());
    }

    public VolunteerFilterForm addFilteringForm(Map<String, String> requestValues) {
        return VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withFreeText(requestValues.get(FILTER_FREE_TEXT))
                .withLocation(requestValues.get(FILTER_LOCATION))
                .withOnlyAvailable(Boolean.valueOf(requestValues.get(FILTER_ONLY_AVAILABLE)))
                .withTypeOfHelps(requestValues.get(FILTER_TYPE_OF_HELPS))
                .build();
    }

    public  Map<String, String> filterAttributes(Map<String, String> requestValues) {
        return requestValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("filter"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

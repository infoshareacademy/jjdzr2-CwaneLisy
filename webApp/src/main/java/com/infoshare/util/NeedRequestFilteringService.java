package com.infoshare.util;

import com.infoshare.dto.NeedRequestFilterForm;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NeedRequestFilteringService {

    private NeedRequestFilteringService() {
    }

    public static final String FILTER_HELP_STATUSES = "filterHelpStatuses";
    public static final String FILTER_TYPE_OF_HELPS = "filterTypeOfHelps";
    public static final String FILTER_START_DATE = "filterStartDate";
    public static final String FILTER_END_DATE = "filterEndDate";
    public static final String FILTER_FREE_TEXT = "filterFreeText";
    public static final String FILTER_LOCATION = "filterLocation";

    public Map<String, String> filterAttributes(Map<String, String> requestValues) {
        return requestValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("filter"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public NeedRequestFilterForm addFilteringForm(Map<String, String> requestValues) {
        return NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
                .withFreeText(requestValues.get(FILTER_FREE_TEXT))
                .withLocation(requestValues.get(FILTER_LOCATION))
                .withStartDate(requestValues.get(FILTER_START_DATE))
                .withEndDate(requestValues.get(FILTER_END_DATE))
                .withTypeOfHelps(requestValues.get(FILTER_TYPE_OF_HELPS))
                .withHelpStatuses(requestValues.get(FILTER_HELP_STATUSES))
                .build();
    }

    public Map<String, Object> createFilteringRedirectAttributes(NeedRequestFilterForm form) {
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Map.of(
                FILTER_FREE_TEXT, form.getFreeText(),
                FILTER_START_DATE, (form.getStartDate() != null) ? form.getStartDate().format(europeanDateFormatter) : "",
                FILTER_END_DATE, (form.getEndDate() != null) ? form.getEndDate().format(europeanDateFormatter) : "",
                FILTER_TYPE_OF_HELPS, form.getTypeOfHelps(),
                FILTER_HELP_STATUSES, form.getHelpStatuses(),
                FILTER_LOCATION, form.getLocation());
    }
}

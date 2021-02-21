package com.infoshare.util;

import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.dto.FilterForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NeedRequestHelper {

    private NeedRequestHelper() {
    }

    public static final String FILTER_HELP_STATUSES = "filterHelpStatuses";
    public static final String FILTER_TYPE_OF_HELPS = "filterTypeOfHelps";
    public static final String FILTER_START_DATE = "filterStartDate";
    public static final String FILTER_END_DATE = "filterEndDate";
    public static final String FILTER_FREE_TEXT = "filterFreeText";
    public static final String FILTER_LOCATION = "filterLocation";

    public static Map<String, String> filterAttributes(Map<String, String> requestValues) {
        return requestValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("filter"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static FilterForm addFilteringForm(Map<String, String> requestValues) {

        Set<TypeOfHelp> typeOfHelp = requestValues.get(FILTER_TYPE_OF_HELPS) != null ?
                Arrays.stream(requestValues.get(FILTER_TYPE_OF_HELPS).split(","))
                        .filter(s -> !s.isEmpty())
                        .map(TypeOfHelp::valueOf).collect(Collectors.toSet()) : null;
        Set<HelpStatuses> helpStatuses = requestValues.get(FILTER_HELP_STATUSES) != null ?
                Arrays.stream(requestValues.get(FILTER_HELP_STATUSES).split(","))
                        .filter(s -> !s.isEmpty())
                        .map(HelpStatuses::valueOf).collect(Collectors.toSet()) : null;
        LocalDate startDate =
                (requestValues.get(FILTER_START_DATE) != null && !requestValues.get(FILTER_START_DATE).isEmpty()) ?
                        LocalDate.parse(requestValues.get(FILTER_START_DATE)) : null;

        LocalDate endDate =
                (requestValues.get(FILTER_END_DATE) != null && !requestValues.get(FILTER_END_DATE).isEmpty()) ?
                        LocalDate.parse(requestValues.get(FILTER_END_DATE)) : null;

        return FilterForm.FilterFormBuilder.aFilterForm()
                .withFreeText(requestValues.get(FILTER_FREE_TEXT))
                .withLocation(requestValues.get(FILTER_LOCATION))
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withTypeOfHelps(typeOfHelp)
                .withHelpStatuses(helpStatuses)
                .build();
    }

    public static Map<String, Object> createFilteringRedirectAttributes(FilterForm form) {
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

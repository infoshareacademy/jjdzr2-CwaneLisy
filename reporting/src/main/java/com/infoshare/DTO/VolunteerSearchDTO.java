package com.infoshare.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class VolunteerSearchDTO {

    private String freeText;
    private String location;
    private List<String> helpTypes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;


    public VolunteerSearchDTO() {

    }


    public VolunteerSearchDTO(String freeText, String location, List<String> helpTypes, LocalDate searchDate) {
        this.freeText = freeText;
        this.location = location;
        this.helpTypes = helpTypes;
        this.searchDate = searchDate;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getHelpTypes() {
        return helpTypes;
    }

    public void setHelpTypes(List<String> helpTypes) {
        this.helpTypes = helpTypes;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }
}

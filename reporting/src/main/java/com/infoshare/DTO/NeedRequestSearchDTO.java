package com.infoshare.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class NeedRequestSearchDTO {

    private String freeText;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private List<String> typeOfHelps;
    private List<String> helpStatuses;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;


    public NeedRequestSearchDTO() {

    }

    public NeedRequestSearchDTO(String freeText, LocalDate startDate, LocalDate endDate, List<String> typeOfHelps, List<String> helpStatuses, String location, LocalDate searchDate) {
        this.freeText = freeText;
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeOfHelps = typeOfHelps;
        this.helpStatuses = helpStatuses;
        this.location = location;
        this.searchDate = searchDate;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getTypeOfHelps() {
        return typeOfHelps;
    }

    public void setTypeOfHelps(List<String> typeOfHelps) {
        this.typeOfHelps = typeOfHelps;
    }

    public List<String> getHelpStatuses() {
        return helpStatuses;
    }

    public void setHelpStatuses(List<String> helpStatuses) {
        this.helpStatuses = helpStatuses;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }
}

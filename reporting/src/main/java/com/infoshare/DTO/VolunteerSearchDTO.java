package com.infoshare.DTO;

import java.util.List;

public class VolunteerSearchDTO {

    private String freeText;
    private String location;
    private List<String> helpTypes;

    public VolunteerSearchDTO(){

    }


    public VolunteerSearchDTO(String freeText, String location, List<String> helpTypes) {
        this.freeText = freeText;
        this.location = location;
        this.helpTypes = helpTypes;
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
}

package com.infoshare.DTO;

public class VolunteerSearchDTO {

    private String freeText;
    private String location;
    private boolean isAvailable;


    public VolunteerSearchDTO(){

    }

    public VolunteerSearchDTO(String freeText, String location, boolean isAvailable) {
        this.freeText = freeText;
        this.location = location;
        this.isAvailable = isAvailable;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

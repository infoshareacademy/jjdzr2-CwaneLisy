package com.infoshare.formobjects;

import com.infoshare.domain.TypeOfHelp;
import com.infoshare.domain.Volunteer;
import com.infoshare.util.ValidatorEnum;
import com.infoshare.validator.RegExpPattern;
import com.infoshare.validator.UniqueMailAndUuid;

import java.util.UUID;

@UniqueMailAndUuid
public class VolunteerForm {
    @RegExpPattern(regexp = ValidatorEnum.POLISHSIGNS)
    private String name;
    @RegExpPattern(regexp = ValidatorEnum.POLISHSIGNS)
    private String location;
    @RegExpPattern(regexp = ValidatorEnum.EMAIL)
    private String email;
    @RegExpPattern(regexp = ValidatorEnum.PHONENUMBER)
    private String phone;
    private TypeOfHelp typeOfHelp;
    private boolean isAvailable;
    private UUID uuid;

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public VolunteerForm() {
    }

    public VolunteerForm(Volunteer volunteer) {
        this.name = volunteer.getName();
        this.location = volunteer.getLocation();
        this.email = volunteer.getEmail();
        this.phone = volunteer.getPhone();
        this.typeOfHelp = volunteer.getTypeOfHelp();
        this.uuid = volunteer.getUuid();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeOfHelp getTypeOfHelp() {
        return typeOfHelp;
    }

    public void setTypeOfHelp(TypeOfHelp typeOfHelp) {
        this.typeOfHelp = typeOfHelp;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}

package com.infoshare.dto;

import com.infoshare.domain.TypeOfHelp;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VolunteerFilterForm {
    private String freeText;
    private Set<TypeOfHelp> typeOfHelps;
    private String location;
    private boolean isAvailable;

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public Set<TypeOfHelp> getTypeOfHelps() {
        return typeOfHelps;
    }

    public void setTypeOfHelps(Set<TypeOfHelp> typeOfHelps) {
        this.typeOfHelps = typeOfHelps;
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


    public static final class VolunteerFilterFormBuilder {
        private String freeText;
        private Set<TypeOfHelp> typeOfHelps;
        private String location;
        private boolean isAvailable;

        private VolunteerFilterFormBuilder() {
        }

        public static VolunteerFilterFormBuilder aVolunteerFilterForm() {
            return new VolunteerFilterFormBuilder();
        }

        public VolunteerFilterFormBuilder withFreeText(String freeText) {
            this.freeText = freeText;
            return this;
        }

        public VolunteerFilterFormBuilder withTypeOfHelps(String typeOfHelps) {
            Optional.ofNullable(typeOfHelps)
                    .filter(Predicate.not(String::isEmpty))
                    .map(s -> Arrays.stream(s.split(",")))
                    .map(stringStream -> stringStream.map(TypeOfHelp::valueOf).collect(Collectors.toSet()))
                    .ifPresent(typeOfHelps1 -> this.typeOfHelps = typeOfHelps1);
            return this;
        }

        public VolunteerFilterFormBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public VolunteerFilterFormBuilder withOnlyAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public VolunteerFilterForm build() {
            VolunteerFilterForm volunteerFilterForm = new VolunteerFilterForm();
            volunteerFilterForm.setFreeText(freeText);
            volunteerFilterForm.setTypeOfHelps(typeOfHelps);
            volunteerFilterForm.setLocation(location);
            volunteerFilterForm.isAvailable = this.isAvailable;
            return volunteerFilterForm;
        }
    }
}

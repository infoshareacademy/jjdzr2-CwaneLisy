package com.infoshare.dto;

import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.TypeOfHelp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

public class NeedRequestFilterForm {

    private String freeText;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Set<TypeOfHelp> typeOfHelps;
    private Set<HelpStatuses> helpStatuses;
    private String location;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Set<HelpStatuses> getHelpStatuses() {
        return helpStatuses;
    }

    public void setHelpStatuses(Set<HelpStatuses> helpStatuses) {
        this.helpStatuses = helpStatuses;
    }

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


    public static final class FilterFormBuilder {
        private String freeText;
        private LocalDate startDate;
        private LocalDate endDate;
        private Set<TypeOfHelp> typeOfHelps;
        private Set<HelpStatuses> helpStatuses ;
        private String location;

        private FilterFormBuilder() {
        }

        public static FilterFormBuilder aFilterForm() {
            return new FilterFormBuilder();
        }

        public FilterFormBuilder withFreeText(String freeText) {
            this.freeText = freeText;
            return this;
        }

        public FilterFormBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public FilterFormBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public FilterFormBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public FilterFormBuilder withTypeOfHelps(Set<TypeOfHelp> typeOfHelps) {
            this.typeOfHelps = typeOfHelps;
            return this;
        }

        public FilterFormBuilder withHelpStatuses(Set<HelpStatuses> helpStatuses) {
            this.helpStatuses = helpStatuses;
            return this;
        }

        public NeedRequestFilterForm build() {
            NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();
            needRequestFilterForm.setFreeText(freeText);
            needRequestFilterForm.setStartDate(startDate);
            needRequestFilterForm.setEndDate(endDate);
            needRequestFilterForm.setTypeOfHelps(typeOfHelps);
            needRequestFilterForm.setHelpStatuses(helpStatuses);
            needRequestFilterForm.setLocation(location);
            return needRequestFilterForm;
        }
    }

    @Override
    public String toString() {
        return "FilterForm{" +
                "freeText='" + freeText + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", typeOfHelps=" + typeOfHelps +
                ", helpStatuses=" + helpStatuses +
                ", location='" + location + '\'' +
                '}';
    }
}

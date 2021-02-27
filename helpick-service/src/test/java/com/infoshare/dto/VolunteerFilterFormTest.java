package com.infoshare.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerFilterFormTest {

    @Test
    void shouldCreateFilteringFormWithCorrectTypesOfHelp(){
        //given
        String typesOfHelp="SHOPPING,WALKING_THE_DOG";
        //when
        VolunteerFilterForm volunteerFilterForm=VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).hasSize(2);
    }
    @Test
    void shouldCreateFilteringFormWithNullTypesOfHelp(){
        //given
        String typesOfHelp=null;
        //when
        VolunteerFilterForm volunteerFilterForm=VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).isNull();
    }
    @Test
    void shouldCreateFilteringFormWithEmptyTypeOfHelp(){
        //given
        String typesOfHelp="";
        //when
        VolunteerFilterForm volunteerFilterForm=VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).isNull();
    }

}
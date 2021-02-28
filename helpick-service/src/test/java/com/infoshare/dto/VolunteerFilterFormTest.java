package com.infoshare.dto;

import com.infoshare.domain.TypeOfHelp;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VolunteerFilterFormTest {

    @Test
    void shouldCreateFilteringFormWithCorrectTypesOfHelp() {
        //given
        String typesOfHelp = "SHOPPING,WALKING_THE_DOG";
        //when
        VolunteerFilterForm volunteerFilterForm = VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).hasSize(2).containsExactlyInAnyOrder(TypeOfHelp.SHOPPING, TypeOfHelp.WALKING_THE_DOG);
    }

    @Test
    void shouldCreateFilteringFormWithNullTypesOfHelp() {
        //given
        String typesOfHelp = null;
        //when
        VolunteerFilterForm volunteerFilterForm = VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).isNull();
    }

    @Test
    void shouldCreateFilteringFormWithEmptyTypeOfHelp() {
        //given
        String typesOfHelp = "";
        //when
        VolunteerFilterForm volunteerFilterForm = VolunteerFilterForm.VolunteerFilterFormBuilder.aVolunteerFilterForm()
                .withTypeOfHelps(typesOfHelp)
                .build();

        //then
        assertThat(volunteerFilterForm.getTypeOfHelps()).isNull();
    }

}
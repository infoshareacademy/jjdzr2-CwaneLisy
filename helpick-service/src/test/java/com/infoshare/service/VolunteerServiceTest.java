package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.domain.TypeOfHelp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.infoshare.domain.Volunteer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class VolunteerServiceTest {

    private final DB db = mock(DB.class);
    private final VolunteerService volunteerService = new VolunteerService(db);

    @Test
    public void ifNewVolunteerRegisteredForValidInput() {
        //Given & when
        boolean isNewVolunteerRegistered = volunteerService.registerNewVolunteer("Anna", "Warszawa", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true);
        //Then
        assertThat(isNewVolunteerRegistered).isTrue();
        }

    @Test
    public void ifNewVolunteerRegisteredWithEmailAlreadyUsedInDb(){
        //Given
        Volunteer volunteerInDb = new Volunteer( "Janko","Gdansk", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true, UUID.randomUUID());
        when(db.getVolunteer("janko@gmail.com")).thenReturn(volunteerInDb);
        //When
        boolean isNewVolunteerRegistered = volunteerService.registerNewVolunteer("Anna", "Warszawa", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true);
        //Then
        assertThat(isNewVolunteerRegistered).isFalse();
    }

}
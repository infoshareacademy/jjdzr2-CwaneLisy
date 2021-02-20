package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.domain.TypeOfHelp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.infoshare.domain.Volunteer;
import org.junit.jupiter.api.Test;

import java.util.Optional;
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

   @Test
   public void testEditVolunteerDataForUuidInDb(){
       //Given
       UUID testUuid = UUID.randomUUID();
       Volunteer volunteerInDb = new Volunteer( "Janko","Gdansk", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true, testUuid);
       when(db.getVolunteer(testUuid)).thenReturn(Optional.of(volunteerInDb));
       //When
       boolean isEditionSuccessful = volunteerService.editVolunteerData("Franko","Gdansk", "franko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true, testUuid);
       //Then
       assertThat(isEditionSuccessful).isTrue();
       verify(db,times(1)).getVolunteer(testUuid);
       verify(db, times(1)).saveVolunteerWithUuid(new Volunteer("Franko","Gdansk", "franko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true, testUuid));
   }

   @Test
    public void testEditVolunteerDataForUuidNotSavedInDb(){
        //Given
       UUID uuid = UUID.randomUUID();
       when(db.getVolunteer((UUID) any())).thenReturn(Optional.empty());
       //When
       boolean isEditionSuccessful = volunteerService.editVolunteerData("Franko","Gdansk", "franko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true, uuid);
       //Then
       assertThat(isEditionSuccessful).isFalse();
       verify(db, times(1)).getVolunteer(uuid);
       verifyNoMoreInteractions(db);
   }


}
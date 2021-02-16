package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.database.FileDb;
import com.infoshare.domain.TypeOfHelp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class VolunteerServiceTest {

    private final DB db = new FileDb();
    private final VolunteerService volunteerService = new VolunteerService(db);

    @Test
    public void ifNewVolunteerRegisteredForValidInput() {
        assertTrue(volunteerService.registerNewVolunteer("Janko", "Gdansk", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true));
        db.deleteVolunteer("janko@gmail.com");
    }

    @Test
    public void ifNewVolunteerRegisteredWithEmailAlreadyUsedInDb(){
        assertTrue(volunteerService.registerNewVolunteer("Janko", "Gdansk", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true));
        assertFalse(volunteerService.registerNewVolunteer("Anna", "Warszawa", "janko@gmail.com", "123654789", TypeOfHelp.SHOPPING, true));
        db.deleteVolunteer("janko@gmail.com");
    }

}
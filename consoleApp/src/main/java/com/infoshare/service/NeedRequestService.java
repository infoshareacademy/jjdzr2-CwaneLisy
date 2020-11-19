package com.infoshare.service;

import com.infoshare.database.FileDb;
import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.PersonInNeed;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.util.Util;
import com.infoshare.util.ValidatorEnum;

import java.io.IOException;
import java.util.Date;

public class NeedRequestService {

    public void createNeedRequest() {
        PersonInNeed personInNeed=createPersonInNeed();
        TypeOfHelp typeOfHelp= Util.createTypeOfHelp();
        NeedRequest needRequest=new NeedRequest(typeOfHelp, HelpStatuses.NEW,new Date(),personInNeed);
        FileDb fileDb= new FileDb();
        try {
            fileDb.saveNeedRequest(needRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private PersonInNeed createPersonInNeed() {
        String name= Util.readDataFromConsole(Util.REGISTRATION_NAME, ValidatorEnum.ALPHA);
        String location=Util.readDataFromConsole(Util.REGISTRATION_LOCATION, ValidatorEnum.ALPHA);
        String phone=Util.readDataFromConsole(Util.REGISTRATION_PHONE_NUMBER, ValidatorEnum.PHONENUMBER);

        return new PersonInNeed(name,location,phone);
    }

}

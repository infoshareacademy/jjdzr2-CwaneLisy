package com.infoshare.database;

import com.infoshare.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileDb implements DB {
    private static final String VOLUNTEER_DB_FILE_NAME = "Volunteer.csv";
    private static final String REQUEST_DB_FILE = "NeedRequest.csv";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // uzywamy df do formatowania i parsowania dat

    public FileDb() {
    }

    @Override // zapis / aktualizacja danych  wolontariusza
    public void saveVolunteer(Volunteer volunteer) throws IOException {

        // Sprawdzamy czy wolontariusz istnieje
        if (getVolunteer(volunteer.getEmail()) == null) {
            FileWriter fileWriter = new FileWriter(VOLUNTEER_DB_FILE_NAME, true);
            fileWriter.write(volunteer.getName() + "," + volunteer.getLocation() + "," + volunteer.getEmail() + ","
                    + volunteer.getPhone() + "," + volunteer.getTypeOfHelp() + "," + volunteer.isAvailable() + "\n");
            fileWriter.close();
        } else {
            // wolontariusz istnieje wiec aktualizujemy jego dane
            FileWriter fileWriter = new FileWriter(VOLUNTEER_DB_FILE_NAME, false);
            List<Volunteer> allVounteers = getVolunteers();
            int index = -1;
            for (int i = 0; i < allVounteers.size(); i++) {

                Volunteer v = allVounteers.get(i);
                if (v.getEmail() == volunteer.getEmail()) {
                    index = i;
                }
            }
            allVounteers.set(index,volunteer);  // gdy chce usunac .remove
            for (Volunteer v:allVounteers) {
                fileWriter.write(v.getName() + "," + v.getLocation() + "," + volunteer.getEmail() + ","
                        + v.getPhone() + "," + v.getTypeOfHelp() + "," + v.isAvailable() + "\n");
            }
            fileWriter.close();
        }
    }

    @Override// zapis zgłoszenia osob potrzebujacej pomocy wraz z rodzajem pomocy
    public void saveNeedRequest(NeedRequest needRequest) throws IOException {
        FileWriter fileWriter = new FileWriter(REQUEST_DB_FILE, true);
        PersonInNeed person = needRequest.getPersonInNeed(); // TODO: zrobic petle? z napisywaniam danych
        fileWriter.write(needRequest.getTypeOfHelp() + "," + needRequest.getHelpStatus() + ","
                + df.format(needRequest.getStatusChange()) + ",");
        fileWriter.write(person.getName() + "," + person.getLocation() + "," + person.getEmail()
                + "," + person.getPhone() + "\n");
        fileWriter.close();
    }

    //odczyt danych osoby potrzebujacej pomocy wraz z rodzajem pomocy
    public List<NeedRequest> getAllNeedRequests() throws FileNotFoundException, ParseException {
        List<NeedRequest> result = new ArrayList<>();
        Scanner scanner = new Scanner(new File(REQUEST_DB_FILE));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splited = line.split(",");
            PersonInNeed person = new PersonInNeed(splited[3], splited[4], splited[5], splited[6]);
            NeedRequest needRequest = new NeedRequest(TypeOfHelp.valueOf(splited[0]), HelpStatuses.valueOf(splited[1])
                    , df.parse(splited[2]), person);
            result.add(needRequest);
        }
        return result;
    }

    @Override // odczyt dost. wolontariuszy , uznalem ze email jest unikatowy dla wolontariusza
    public Volunteer getVolunteer(String email) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(VOLUNTEER_DB_FILE_NAME));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] volunteerAtributes = line.split(",");
            if (volunteerAtributes.length >= 6 && volunteerAtributes[2].equals(email)) {
                return new Volunteer(volunteerAtributes[0], volunteerAtributes[1], volunteerAtributes[2],
                        volunteerAtributes[3], volunteerAtributes[4], Boolean.parseBoolean(volunteerAtributes[5]));
            }
        }
        return null;
    }

    @Override
    public List<Volunteer> getVolunteers() throws FileNotFoundException {
        List<Volunteer> result = new ArrayList<>();
        Scanner scanner = new Scanner(new File(VOLUNTEER_DB_FILE_NAME));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] volunteerAtributes = line.split(",");
            result.add(new Volunteer(volunteerAtributes[0], volunteerAtributes[1], volunteerAtributes[2],
                    volunteerAtributes[3], volunteerAtributes[4], Boolean.parseBoolean(volunteerAtributes[5])));
        }
        return result;

    }

    @Override
    public List<PersonInNeed> getPersonInNeed() throws FileNotFoundException {
        return null;
    }

}
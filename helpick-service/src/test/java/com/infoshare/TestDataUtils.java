package com.infoshare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.PersonInNeed;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.dto.NeedRequestListObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDataUtils {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<NeedRequestListObject> getNeedRequestListObject(String fileName) {
        List<NeedRequestListObject> needRequestListObjects = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            needRequestListObjects = Arrays.asList(objectMapper.readValue(new File(TestDataUtils.class.getClassLoader().getResource(fileName).getFile()), NeedRequestListObject[].class));
        } catch (IOException e) {
            System.out.println("Nie udało się wczytać pliku");
        }
        return needRequestListObjects;
    }

    public static List<NeedRequest> getNeedRequests() {

        List<NeedRequest> result = new ArrayList<>();
        try {
            try (Scanner scanner = new Scanner(new File(TestDataUtils.class.getClassLoader().getResource("NeedRequest.csv").getFile()))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] splited = line.split(",");
                    PersonInNeed person = new PersonInNeed(splited[3], splited[4], splited[5]);
                    NeedRequest needRequest = new NeedRequest(TypeOfHelp.valueOf(splited[0]),
                            HelpStatuses.valueOf(splited[1]), df.parse(splited[2]), person, UUID.fromString(splited[6]));
                    result.add(needRequest);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}

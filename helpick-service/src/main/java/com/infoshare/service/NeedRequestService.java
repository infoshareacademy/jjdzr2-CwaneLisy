package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.PersonInNeed;
import com.infoshare.domain.TypeOfHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NeedRequestService {

    DB db;

    @Autowired
    public NeedRequestService(DB db) {
        this.db = db;
    }

    public void createNeedRequest(String name, String location, String phone, TypeOfHelp typeOfHelp, UUID uuid) {
        PersonInNeed personInNeed = new PersonInNeed(name, location, phone);
        NeedRequest needRequest;
        if (null == uuid) {
            needRequest = NeedRequest
                    .create(typeOfHelp, personInNeed);
            db.saveNeedRequest(needRequest);
        } else {
            Optional<NeedRequest> needRequestOptional = db.getNeedRequests().stream()
                    .filter(needRequest1 -> needRequest1.getUuid().equals(uuid))
                    .findAny();
            if (needRequestOptional.isPresent()) {
                needRequest = needRequestOptional.get();
                needRequest.setPersonInNeed(personInNeed);
                needRequest.setTypeOfHelp(typeOfHelp);
                db.saveNeedRequest(needRequest);
            }
        }

    }

    public void createNeedRequest(String name, String location, String phone, TypeOfHelp typeOfHelp) {
        createNeedRequest(name, location, phone, typeOfHelp, null);
    }

    public void changeRequestStatus(List<NeedRequest> filteredList, int choice) {
        List<NeedRequest> activeNeedRequests = db.getNeedRequests();
        NeedRequest changedRequest = filteredList.get(choice - 1);
        changedRequest.setHelpStatus(HelpStatuses.INPROGRESS);
        changedRequest.setStatusChange(new Date());
        for (int i = 0; i < activeNeedRequests.size(); i++) {
            if (activeNeedRequests.get(i).getUuid().equals(changedRequest.getUuid())) {
                activeNeedRequests.set(i, changedRequest);
            }
        }
        db.saveUpdatedNeedRequest(activeNeedRequests);
    }

    public void restoreStatusForExpiredRequests() {
        boolean hasListChanged = false;
        List<NeedRequest> activeNeedRequests = db.getNeedRequests();
        for (NeedRequest request : activeNeedRequests) {
            Date time1 = request.getStatusChange();
            Date actualTime = new Date();
            long diff = actualTime.getTime() - time1.getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (request.getHelpStatus().equals(HelpStatuses.INPROGRESS) &&
                    minutes > 1440) {
                hasListChanged = true;
                log.info("Found difference > 24h in request " + request.getUuid() + ", changing status...");
                request.setHelpStatus(HelpStatuses.NEW);
                request.setStatusChange(new Date());
                log.info("Status of request ID " + request.getUuid() + " restored to NEW");
            }
        }
        if (hasListChanged) {
            db.saveUpdatedNeedRequest(activeNeedRequests);
        }
    }

    public List<NeedRequest> getNeedRequestFilteredList(String city, TypeOfHelp typeOfHelp) {
        return db.getNeedRequests().stream()
                .filter(n -> n.getHelpStatus().equals(HelpStatuses.NEW))
                .filter(n -> n.getTypeOfHelp().equals(typeOfHelp))
                .filter(n -> n.getPersonInNeed().getLocation().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public Optional<NeedRequest> getNeedRequestById(UUID uuid) {
        return db.getNeedRequests().stream()
                .filter(n -> n.getUuid().equals(uuid))
                .findAny();
    }

    public List<NeedRequest> getAllNeedRequests() {
        return db.getNeedRequests();
    }

    public List<TypeOfHelp> getTypesOfHelp() {
        return Arrays.asList(TypeOfHelp.values());
    }
}

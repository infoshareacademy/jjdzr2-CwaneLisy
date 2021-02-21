package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.PersonInNeed;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.dto.FilterForm;
import com.infoshare.dto.NeedRequestListObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
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

    private NeedRequestListObject convertToNeedRequestForm(NeedRequest needRequest) {
        return NeedRequestListObject.NeedRequestListObjectBuilder.aNeedRequestListObject()
                .withUuid(needRequest.getUuid())
                .withName(needRequest.getPersonInNeed().getName())
                .withPhone(needRequest.getPersonInNeed().getPhone())
                .withLocation(needRequest.getPersonInNeed().getLocation())
                .withTypeOfHelp(needRequest.getTypeOfHelp())
                .withStatusChange(needRequest.getStatusChange())
                .withHelpStatus(needRequest.getHelpStatus())
                .build();
    }

    private boolean filterHelpStatus(FilterForm filterForm, NeedRequest needRequest) {
        return (filterForm.getHelpStatuses() == null ||
                filterForm.getHelpStatuses().isEmpty()) || filterForm.getHelpStatuses().stream()
                .anyMatch(helpStatuses -> needRequest.getHelpStatus().equals(helpStatuses));
    }

    private boolean filterTypeOfHelp(FilterForm filterForm, NeedRequest needRequest) {
        return (filterForm.getTypeOfHelps() == null || filterForm.getTypeOfHelps().isEmpty()) || filterForm.getTypeOfHelps().stream()
                .anyMatch(typeOfHelp -> needRequest.getTypeOfHelp().equals(typeOfHelp));
    }

    private boolean filterDates(FilterForm filterForm, NeedRequest needRequest) {
        boolean isFilterStartDateBeforeNeedRequestDate = (filterForm.getStartDate() == null) ||
                filterForm.getStartDate().isBefore(convertToLocalDateViaInstant(needRequest.getStatusChange())) ||
                filterForm.getStartDate().isEqual(convertToLocalDateViaInstant(needRequest.getStatusChange()));
        boolean isFilterEndDateAfterNeedRequestDate = (filterForm.getEndDate() == null) ||
                filterForm.getEndDate().isAfter(convertToLocalDateViaInstant(needRequest.getStatusChange())) ||
                filterForm.getEndDate().isEqual(convertToLocalDateViaInstant(needRequest.getStatusChange()));
        return isFilterStartDateBeforeNeedRequestDate && isFilterEndDateAfterNeedRequestDate;
    }

    private boolean filterLocation(FilterForm filterForm, NeedRequest needRequest) {
        return (filterForm.getLocation() == null || filterForm.getLocation().isEmpty()) || needRequest.getPersonInNeed().getLocation().equalsIgnoreCase(filterForm.getLocation());
    }

    private boolean filterFreeText(FilterForm filterForm, NeedRequest needRequest) {
        return (filterForm.getFreeText() == null || filterForm.getFreeText().isEmpty()) ||
                needRequest.getPersonInNeed().getName().toLowerCase().contains(filterForm.getFreeText().toLowerCase()) ||
                needRequest.getPersonInNeed().getLocation().toLowerCase().contains(filterForm.getFreeText().toLowerCase()) ||
                needRequest.getPersonInNeed().getPhone().contains(filterForm.getFreeText());
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public List<NeedRequestListObject> getRequestFilteredList(FilterForm filterForm) {
        List<NeedRequestListObject> needRequestListObjects = db.getNeedRequests().stream()
                .filter(needRequest -> filterHelpStatus(filterForm, needRequest))
                .filter(needRequest -> filterTypeOfHelp(filterForm, needRequest))
                .filter(needRequest -> filterDates(filterForm, needRequest))
                .filter(needRequest -> filterLocation(filterForm, needRequest))
                .filter(needRequest -> filterFreeText(filterForm, needRequest))
                .map(this::convertToNeedRequestForm)
                .collect(Collectors.toList());

        log.info("For following query params {} found {} records", filterForm, needRequestListObjects.size());
        return needRequestListObjects;
    }

    public void updateNeedRequest(String name, String location, String phone, TypeOfHelp typeOfHelp, UUID uuid) {
        Optional<NeedRequest> needRequestOptional = db.getNeedRequests().stream()
                .filter(needRequest1 -> needRequest1.getUuid().equals(uuid))
                .findAny();
        if (needRequestOptional.isPresent()) {
            NeedRequest needRequest = needRequestOptional.get();
            needRequest.getPersonInNeed().setLocation(location);
            needRequest.getPersonInNeed().setName(name);
            needRequest.getPersonInNeed().setPhone(phone);
            needRequest.setTypeOfHelp(typeOfHelp);
            db.saveNeedRequest(needRequest);
        }
    }

    public void createNeedRequest(String name, String location, String phone, TypeOfHelp typeOfHelp) {
        PersonInNeed personInNeed = new PersonInNeed(name, location, phone);
        NeedRequest needRequest = NeedRequest.create(typeOfHelp, personInNeed);
        db.saveNeedRequest(needRequest);
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

    public List<HelpStatuses> getHelpStatuses() {
        return Arrays.asList(HelpStatuses.values());
    }
}

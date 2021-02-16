package com.infoshare.database;

import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.Volunteer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DB {

    void saveVolunteer(Volunteer volunteer);

    void saveVolunteerWithUuid(Volunteer volunteer);

    List<Volunteer> getVolunteers();

    void saveNeedRequest(NeedRequest needRequest);

    void saveUpdatedNeedRequest(List <NeedRequest> needRequestList);

    List<NeedRequest> getNeedRequests();

    Volunteer getVolunteer(String email);

    Optional<Volunteer> getVolunteer(UUID uuid);

    void deleteVolunteer(String email);
}

package com.infoshare.database;

import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.Volunteer;
import com.infoshare.repository.NeedRequestDao;
import com.infoshare.repository.VolunteerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.management.Query;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Qualifier("RelationalDb")
public class RelationalDb implements DB {

    private NeedRequestDao needRequestDao;
    private VolunteerDao volunteerDao;

    @Autowired
    public RelationalDb(final NeedRequestDao needRequestDao, VolunteerDao volunteerDao) {
        this.needRequestDao = needRequestDao;
        this.volunteerDao = volunteerDao;
    }

    @Override
    public void saveVolunteer(Volunteer volunteer) {
        volunteerDao.save(volunteer);
    }

    @Override
    public void saveVolunteerWithUuid(Volunteer volunteer) {

    }

    @Override
    public List<Volunteer> getVolunteers() {
        return (List<Volunteer>) volunteerDao.findAll();
    }

    @Override
    public void saveNeedRequest(NeedRequest needRequest) {

    }

    @Override
    public void saveUpdatedNeedRequest(List<NeedRequest> needRequestList) {

    }

    @Override
    public List<NeedRequest> getNeedRequests() {
        return null;
    }

    @Override
    public Volunteer getVolunteer(String email) {

        return volunteerDao.getVolunteer(email);
    }

    @Override
    public Optional<Volunteer> getVolunteer(UUID uuid) {
        return Optional.empty();
    }
}

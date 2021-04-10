package com.infoshare.repositories;

import com.infoshare.domain.VolunteerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface VolunteerStatsRepository extends JpaRepository<VolunteerStatistics, UUID> {

    @Query(
            value = "SELECT count(v) FROM VolunteerStatistics v WHERE v.searchDate = ?1"
    )
    Long getSearchCountByDate(LocalDate date);

}

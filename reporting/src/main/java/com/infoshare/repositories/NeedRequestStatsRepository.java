package com.infoshare.repositories;

import com.infoshare.domain.NeedRequestStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface NeedRequestStatsRepository extends JpaRepository<NeedRequestStatistics, UUID> {

    @Query(
            value = "SELECT count(n) FROM NeedRequestStatistics n WHERE n.searchDate = ?1"
    )
    Long getSearchCountByDate(LocalDate date);
}

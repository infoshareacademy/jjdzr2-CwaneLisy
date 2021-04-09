package com.infoshare.repositories;

import com.infoshare.domain.VolunteerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VolunteerStatsRepository extends JpaRepository<VolunteerStatistics, UUID> {


}

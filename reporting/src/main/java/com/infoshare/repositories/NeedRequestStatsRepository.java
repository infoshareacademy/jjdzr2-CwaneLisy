package com.infoshare.repositories;

import com.infoshare.domain.NeedRequestStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NeedRequestStatsRepository extends JpaRepository<NeedRequestStatistics, UUID> {

}

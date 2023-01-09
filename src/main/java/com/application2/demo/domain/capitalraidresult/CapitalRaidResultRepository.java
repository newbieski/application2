package com.application2.demo.domain.capitalraidresult;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.*;

public interface CapitalRaidResultRepository extends JpaRepository<CapitalRaidResult, Long> {
    List<CapitalRaidResult> findAllByEndTimeBetween(LocalDateTime start, LocalDateTime end);
}

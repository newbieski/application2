package com.application2.demo.domain.clanwar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClanWarSummaryRepository extends JpaRepository<ClanWarSummary, Long> {
    List<ClanWarSummary> findAllByEndTimeBetween(LocalDateTime start, LocalDateTime end);
}

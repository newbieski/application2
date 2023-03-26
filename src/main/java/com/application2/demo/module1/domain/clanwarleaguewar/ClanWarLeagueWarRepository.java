package com.application2.demo.module1.domain.clanwarleaguewar;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.*;

public interface ClanWarLeagueWarRepository extends JpaRepository<ClanWarLeagueWar, Long> {
    List<ClanWarLeagueWar> findAllByEndTimeBetween(LocalDateTime start, LocalDateTime end);
}

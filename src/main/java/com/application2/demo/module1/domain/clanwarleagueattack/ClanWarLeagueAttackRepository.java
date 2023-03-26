package com.application2.demo.module1.domain.clanwarleagueattack;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ClanWarLeagueAttackRepository extends JpaRepository<ClanWarLeagueAttack, Long> {
    List<ClanWarLeagueAttack> findAllByWarTag(String warTag);
}

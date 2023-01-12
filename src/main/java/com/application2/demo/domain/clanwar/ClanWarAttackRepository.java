package com.application2.demo.domain.clanwar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClanWarAttackRepository extends JpaRepository<ClanWarAttack, Long> {
    List<ClanWarAttack> findAllByclanwarId(long clanwarId);
}

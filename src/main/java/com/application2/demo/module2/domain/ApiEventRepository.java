package com.application2.demo.module2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiEventRepository extends JpaRepository<ApiEvent, Long> {
    List<ApiEvent> findAllByEventTimeAndEventCode(LocalDateTime eventTime, long eventCode);
}

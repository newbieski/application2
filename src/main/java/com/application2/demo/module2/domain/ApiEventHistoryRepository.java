package com.application2.demo.module2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiEventHistoryRepository extends JpaRepository<ApiEventHistory, Long> {
}

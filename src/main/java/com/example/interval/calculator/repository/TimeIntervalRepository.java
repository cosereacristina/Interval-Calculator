package com.example.interval.calculator.repository;

import com.example.interval.calculator.entity.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long> {
}

package com.example.interval.calculator.controllers;

import com.example.interval.calculator.entity.TimeInterval;
import com.example.interval.calculator.service.CalculateIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interval")
public class CalculatorController {

    @Autowired
    CalculateIntervalService calculateIntervalService;

    @PostMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeInterval> calculateIntervals(@RequestBody final List<TimeInterval> timeInterval) {
        calculateIntervalService.calculateInterval(timeInterval);
        return calculateIntervalService.getAllIntervals();
    }

}
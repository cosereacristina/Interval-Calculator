package com.example.interval.calculator.service;

import com.example.interval.calculator.entity.TimeInterval;
import com.example.interval.calculator.repository.TimeIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CalculateIntervalService {

    @Autowired
    TimeIntervalRepository timeIntervalRepository;

    public List<TimeInterval> getAllIntervals() {
        return timeIntervalRepository.findAll();
    }

    public void calculateInterval(List<TimeInterval> timeIntervals) {
        List<TimeInterval> calculatedIntervals = new ArrayList<>();

        for (TimeInterval timeInterval : timeIntervals) {

            TimeInterval calculatedInterval = new TimeInterval();

            calculatedInterval.setId(timeInterval.getId());
            calculatedInterval.setStart(timeInterval.getStart());
            calculatedInterval.setEnd(timeInterval.getEnd());

            String duration = getDuration(calculatedInterval.getStart(), calculatedInterval.getEnd());
            calculatedInterval.setDuration(duration);

            int indexOfTimeInterval = timeIntervals.indexOf(timeInterval);
            if (indexOfTimeInterval > 0) {
                TimeInterval lastTimeInterval = timeIntervals.get(indexOfTimeInterval - 1);
                String breakPeriod = getDuration(lastTimeInterval.getEnd(), calculatedInterval.getStart());
                if (breakPeriod.contains("duration")) {
                    calculatedInterval.setBreakPeriod(breakPeriod.replace("duration", "period"));
                } else calculatedInterval.setBreakPeriod(breakPeriod);
            }

            calculatedIntervals.add(calculatedInterval);
        }
        timeIntervalRepository.saveAll(calculatedIntervals);
    }

    private String getDuration(Date startDateTime, Date endDateTime) {
        String duration;

        long end = endDateTime.getTime();
        long start = startDateTime.getTime();

        if (end > start) {
            long durationTime = end - start;
            String durationMin = TimeUnit.MILLISECONDS.toMinutes(durationTime) % 60 + "m";
            String durationHours = TimeUnit.MILLISECONDS.toHours(durationTime) % 24 + "h";
            String durationDays = TimeUnit.MILLISECONDS.toDays(durationTime) % 365 + "d";
            String durationYears = TimeUnit.MILLISECONDS.toDays(durationTime) / 365L + "y";

            duration = String.format("%s%s%s%s", durationYears, durationDays, durationHours, durationMin);

            if (durationMin.equals("0m")) {
                duration = duration.replace(durationMin, "");
            }
            if (durationHours.equals("0H")) {
                duration = duration.replace(durationHours, "");
            }
            if (durationDays.equals("0d")) {
                duration = duration.replace(durationDays, "");
            }
            if (durationYears.equals("0y")) {
                duration = duration.replace(durationYears, "");
            }
        } else {
            duration = "Cannot calculate duration";
        }

        return duration;
    }
}
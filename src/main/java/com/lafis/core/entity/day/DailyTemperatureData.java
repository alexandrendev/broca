package com.lafis.core.entity.day;

import java.time.LocalDate;

public record DailyTemperatureData(
        LocalDate date,
        double maxTemperature,
        double temperature
) {
}

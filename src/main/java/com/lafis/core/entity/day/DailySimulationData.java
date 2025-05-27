package com.lafis.core.entity.day;

import java.time.LocalDate;

public record DailySimulationData(
        LocalDate date,
        int hosts,
        int infected,
        int parasitoid
) {
}

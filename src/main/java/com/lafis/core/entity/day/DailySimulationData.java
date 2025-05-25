package com.lafis.core.entity.day;

public record DailySimulationData(
        int day,
        int hosts,
        int infected,
        int parasitoid
) {
}

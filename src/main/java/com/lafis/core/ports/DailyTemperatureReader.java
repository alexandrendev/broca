package com.lafis.core.ports;

import com.lafis.core.entity.day.DailyTemperatureData;

import java.time.LocalDate;
import java.util.Map;

public interface DailyTemperatureReader {
    Map<LocalDate, DailyTemperatureData> loadAllDailyTemperatureData(String filePath);
}

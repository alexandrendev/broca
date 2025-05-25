package com.lafis.core.ports;

import com.lafis.core.entity.day.DailySimulationData;

import java.util.List;

public interface DailySimulationDataRepository {

    void append(DailySimulationData data);

    void saveAll(List<DailySimulationData> data);

    void initialize(String fileName);

    void close();
}

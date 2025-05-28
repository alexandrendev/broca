package com.lafis.core.ports;

import com.lafis.core.entity.day.DailySimulationData;

import java.util.List;

public interface ChartPlotter {

    void plotChart(List<DailySimulationData> dataList);
}

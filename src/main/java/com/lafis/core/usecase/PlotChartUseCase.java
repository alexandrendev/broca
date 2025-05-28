package com.lafis.core.usecase;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.ports.DailySimulationDataRepository;
import com.lafis.infra.chart.ChartProvider;

import java.util.List;

public class PlotChartUseCase {
    private final ChartProvider chartProvider;
    DailySimulationDataRepository dailySimulationDataRepository;

    public void execute(String fileName) {
        dailySimulationDataRepository.initialize(fileName, false);

        List<DailySimulationData> data = dailySimulationDataRepository.getData();
        chartProvider.plotChart(data);
    }

    public PlotChartUseCase(ChartProvider chartProvider, DailySimulationDataRepository dailySimulationDataRepository) {
        this.chartProvider = chartProvider;
        this.dailySimulationDataRepository = dailySimulationDataRepository;
    }
}

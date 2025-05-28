package com.lafis.controller;

import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.ports.DailyTemperatureReader;
import com.lafis.core.usecase.PlotChartUseCase;
import com.lafis.core.usecase.RunSimulationUseCase;

public class SimulationController {
    private RunSimulationUseCase runSimulationUseCase;
    private PlotChartUseCase plotChartUseCase;

    public void runSimulation(PopulationData populationData,
                              String simulationIdentifier,
                              DailyTemperatureReader dailyTemperatureReader,
                              String dailyTemperatureFilePath
    ) {
        runSimulationUseCase.execute(populationData, simulationIdentifier, dailyTemperatureReader, dailyTemperatureFilePath);
    }

    public void plotChart(String fileName) {
        plotChartUseCase.execute(fileName);
    }

    public SimulationController(RunSimulationUseCase runSimulationUseCase, PlotChartUseCase plotChartUseCase) {
        this.runSimulationUseCase = runSimulationUseCase;
        this.plotChartUseCase = plotChartUseCase;
    }
}

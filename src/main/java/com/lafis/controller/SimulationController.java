package com.lafis.controller;

import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.ports.DailyTemperatureReader;
import com.lafis.core.usecase.RunSimulationUseCase;
import com.lafis.infra.random.RandomProvider;

public class SimulationController {
    private RunSimulationUseCase runSimulationUseCase;

    public void runSimulation(PopulationData populationData,
                              String simulationIdentifier,
                              DailyTemperatureReader dailyTemperatureReader,
                              String dailyTemperatureFilePath
    ) {
        runSimulationUseCase.execute(populationData, simulationIdentifier, dailyTemperatureReader, dailyTemperatureFilePath);
    }

    public SimulationController(RunSimulationUseCase runSimulationUseCase) {
        this.runSimulationUseCase = runSimulationUseCase;
    }
}

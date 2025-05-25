package com.lafis.controller;

import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.usecase.RunSimulationUseCase;

public class SimulationController {
    private RunSimulationUseCase runSimulationUseCase;

    public void runSimulation(int days, PopulationData populationData, String simulationIdentifier){
        runSimulationUseCase.execute(days, populationData, simulationIdentifier);
    }

    public SimulationController(RunSimulationUseCase runSimulationUseCase) {
        this.runSimulationUseCase = runSimulationUseCase;
    }
}

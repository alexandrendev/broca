package com.lafis;

import com.lafis.controller.SimulationController;
import com.lafis.core.entity.equation.Equation;
import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.usecase.RunSimulationUseCase;
import com.lafis.infra.EquationFactory;
import com.lafis.infra.Equilibrium;
import com.lafis.infra.random.RandomProvider;
import com.lafis.infra.repository.DailySimulationDataCsvRepository;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        PopulationData data = new PopulationData(3000, 600, 3000);
        RandomProvider randomProvider = new RandomProvider(new Random());
        DailySimulationDataCsvRepository repository;
        try {
            repository = new DailySimulationDataCsvRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RunSimulationUseCase runSimulationUseCase = new RunSimulationUseCase(
                EquationFactory.create(Equilibrium.FIRST),
                randomProvider,
                repository
        );
        SimulationController controller = new SimulationController(runSimulationUseCase);

        controller.runSimulation(15,data, "equilibrio-1-2");

    }
}
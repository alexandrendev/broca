package com.lafis;

import com.lafis.controller.SimulationController;
import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.usecase.RunSimulationUseCase;
import com.lafis.infra.BetaProvider;
import com.lafis.infra.EquationFactory;
import com.lafis.infra.Equilibrium;
import com.lafis.infra.random.RandomProvider;
import com.lafis.infra.repository.DailySimulationDataCsvRepository;
import com.lafis.infra.temperature.DailyTemperatureProvider;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        PopulationData data = new PopulationData(3000, 600, 3000);
        RandomProvider randomProvider = new RandomProvider(new Random());
        DailySimulationDataCsvRepository repository;
        BetaProvider betaProvider = new BetaProvider();


        try {
            repository = new DailySimulationDataCsvRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RunSimulationUseCase runSimulationUseCase = new RunSimulationUseCase(
                EquationFactory.create(Equilibrium.SECOND),
                randomProvider,
                repository,
                betaProvider
        );
        SimulationController controller = new SimulationController(runSimulationUseCase);

        DailyTemperatureProvider temperatureProvider = new DailyTemperatureProvider();
        String dailyTemperatureFilePath = "input/input.txt";

        controller.runSimulation(data, "equilibrio-2-temp", temperatureProvider, dailyTemperatureFilePath);

    }
}
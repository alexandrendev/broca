package com.lafis;

import com.lafis.controller.SimulationController;
import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.usecase.PlotChartUseCase;
import com.lafis.core.usecase.RunSimulationUseCase;
import com.lafis.infra.BetaProvider;
import com.lafis.infra.EquationFactory;
import com.lafis.infra.Equilibrium;
import com.lafis.infra.chart.ChartProvider;
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

        String fileName = "temp_THIRD_EQ";

        try {
            repository = new DailySimulationDataCsvRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RunSimulationUseCase runSimulationUseCase = new RunSimulationUseCase(
                EquationFactory.create(Equilibrium.THIRD),
                randomProvider,
                repository,
                betaProvider
        );

        ChartProvider chartProvider = new ChartProvider();

        PlotChartUseCase plotChartUseCase = new PlotChartUseCase(chartProvider , repository);
        SimulationController controller = new SimulationController(runSimulationUseCase , plotChartUseCase);

        DailyTemperatureProvider temperatureProvider = new DailyTemperatureProvider();
        String dailyTemperatureFilePath = "input/input.txt";

        controller.runSimulation(data, fileName, temperatureProvider, dailyTemperatureFilePath);

        controller.plotChart(fileName);

    }
}
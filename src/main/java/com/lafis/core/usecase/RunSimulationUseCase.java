package com.lafis.core.usecase;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.entity.day.DailyTemperatureData;
import com.lafis.core.entity.equation.Equation;
import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.Individual;
import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.ports.BetaCalculator;
import com.lafis.core.ports.DailySimulationDataRepository;
import com.lafis.core.ports.DailyTemperatureReader;
import com.lafis.core.ports.RandomGenerator;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;

public class RunSimulationUseCase {

    private final Equation equation;
    private final RandomGenerator randomGenerator;
    private final DailySimulationDataRepository dailyDataRepository;
    private final BetaCalculator betaCalculator;

    public void execute(PopulationData initialPopulation,
                        String simulationIdentifier,
                        DailyTemperatureReader dailyTemperatureReader,
                        String dailyTemperatureFilePath
    ){

        var host = new Individual(initialPopulation.hosts(), randomGenerator);
        var infected = new Individual(initialPopulation.infecteds(), randomGenerator);
        var parasitoid = new Individual(initialPopulation.parasitoids(), randomGenerator);

        Map<LocalDate, DailyTemperatureData> dailyTemperatureDataMap = dailyTemperatureReader.loadAllDailyTemperatureData(dailyTemperatureFilePath);
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);

        dailyDataRepository.initialize(simulationIdentifier, true);
        for(LocalDate day = start; day.isBefore(end); day = day.plusDays(1)) {
            PopulationData currentPopSnapshot = new PopulationData(
                    host.getCurrentPopulation(),
                    infected.getCurrentPopulation(),
                    parasitoid.getCurrentPopulation()
            );

            DailySimulationData dailyRecord = new DailySimulationData(
                    day,
                    host.getCurrentPopulation(),
                    infected.getCurrentPopulation(),
                    parasitoid.getCurrentPopulation()
            );

            dailyDataRepository.append(dailyRecord);


            double maxTemperature = dailyTemperatureDataMap.get(day).maxTemperature();
            double temperature = dailyTemperatureDataMap.get(day).temperature();


            double calculatedBeta = this.betaCalculator.calculateBeta(equation.getBeta(), temperature, maxTemperature);

            List<Interval> firstLineInterval = equation.getFirstLineIntervals(currentPopSnapshot, calculatedBeta);
            int infectedUpdate = host.monteCarlo(firstLineInterval);

            List<Interval> secondLineInterval = equation.getSecondLineIntervals(currentPopSnapshot, calculatedBeta);
            int parasitoidUpdate = infected.monteCarlo(secondLineInterval);

            List<Interval> thirdLineInterval = equation.getThirdLineIntervals(currentPopSnapshot);
            parasitoid.monteCarlo(thirdLineInterval);

            infected.updateCurrentPopulation(infectedUpdate);
            parasitoid.updateCurrentPopulation(parasitoidUpdate);

        }

        dailyDataRepository.close();
    }

    public RunSimulationUseCase(Equation equation, RandomGenerator randomGenerator, DailySimulationDataRepository dailyDataRepository, BetaCalculator betaCalculator) {
        this.equation = equation;
        this.randomGenerator = randomGenerator;
        this.dailyDataRepository = dailyDataRepository;
        this.betaCalculator = betaCalculator;
    }
}
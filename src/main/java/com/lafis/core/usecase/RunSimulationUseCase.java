package com.lafis.core.usecase;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.entity.equation.Equation;
import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.Individual;
import com.lafis.core.entity.population.PopulationData;
import com.lafis.core.ports.DailySimulationDataRepository;
import com.lafis.core.ports.RandomGenerator;

import java.util.List;

public class RunSimulationUseCase {

    private final Equation equation;
    private final RandomGenerator randomGenerator;
    private final DailySimulationDataRepository dailyDataRepository;

    public void execute(int days, PopulationData initialPopulation, String simulationIdentifier) {
        var host = new Individual(initialPopulation.hosts(), randomGenerator);
        var infected = new Individual(initialPopulation.infecteds(), randomGenerator);
        var parasitoid = new Individual(initialPopulation.parasitoids(), randomGenerator);

        dailyDataRepository.initialize(simulationIdentifier);

        for (int day = 1; day <= days; day++) {

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

            if(day < days){

                List<Interval> firstLineInterval = equation.getFirstLineIntervals(currentPopSnapshot);
                List<Interval> secondLineInterval = equation.getSecondLineIntervals(currentPopSnapshot);
                List<Interval> thirdLineInterval = equation.getThirdLineIntervals(currentPopSnapshot);

                host.monteCarlo(firstLineInterval);
                infected.monteCarlo(secondLineInterval);
                parasitoid.monteCarlo(thirdLineInterval);
            }


        }
    }

    public RunSimulationUseCase(Equation equation, RandomGenerator randomGenerator, DailySimulationDataRepository dailyDataRepository) {
        this.equation = equation;
        this.randomGenerator = randomGenerator;
        this.dailyDataRepository = dailyDataRepository;
    }
}

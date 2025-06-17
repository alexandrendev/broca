package com.lafis.core.entity.population;

import com.lafis.core.entity.interval.Effect;
import com.lafis.core.entity.interval.Interval;
import com.lafis.core.ports.RandomGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Individual {
    private int initialPopulation;
    private int currentPopulation;
    private final RandomGenerator random;

    public int monteCarlo(List<Interval> intervals) {
        intervals = intervals.stream()
                .sorted(Comparator.comparing(Interval::getValue))
                .collect(Collectors.toList());

        int affected = 0;
        double maxValue = intervals.get(intervals.size() - 1).getValue(); // Pega o último valor

        for (int i = 0; i < currentPopulation; i++) {
            double rand = random.nextDouble() * maxValue;

            // Abordagem direta por índices como no Python
            if (rand > 0 && rand <= intervals.get(0).getValue()) {
                applyEffect(intervals.get(0).getEffect());
            }
            else if (rand <= intervals.get(1).getValue()) {
                applyEffect(intervals.get(1).getEffect());
            }
            else if (intervals.size() > 2 && rand <= intervals.get(2).getValue()) {
                applyEffect(intervals.get(2).getEffect());
            }


//            if (intervals.size() > 2 && intervals.get(2).getEffect() == Effect.INFECTED) {
//                affected++;
//            }
        }

        if (currentPopulation < 0) currentPopulation = 0;
        return affected;
    }

    private void applyEffect(Effect effect) {
        switch (effect) {
            case NATURAL_DEATH -> currentPopulation--;
            case DEATH_DUE_TO_PARASITE, INFECTED -> currentPopulation--;
            case REPRODUCE -> currentPopulation++;
        }
    }


//    public int monteCarlo(List<Interval> intervals) {
//        int affected = 0;
//
//        for (int i = 0; i < currentPopulation; i++) {
//            double rand = random.nextDouble();
//
//            double cumulative = 0.0;
//            for (Interval interval : intervals) {
//                cumulative += interval.getValue();
//                if (rand <= cumulative) {
//                    switch (interval.getEffect()) {
//                        case NATURAL_DEATH -> currentPopulation--;
//                        case DEATH_DUE_TO_PARASITE, INFECTED -> {
//                            currentPopulation--;
//                            affected++;
//                        }
//                        case REPRODUCE -> currentPopulation++;
//                    }
//                    break;
//                }
//            }
//        }
//
//        if (currentPopulation < 0) currentPopulation = 0;
//        return affected;
//    }

    public void updateCurrentPopulation(int value) {
        this.currentPopulation += value;
    }

    public Individual(int initialPopulation, RandomGenerator random) {
        this.initialPopulation = initialPopulation;
        this.currentPopulation = initialPopulation;
        this.random = random;
    }

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public void setInitialPopulation(int initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    public int getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(int currentPopulation) {
        this.currentPopulation = currentPopulation;
    }
}

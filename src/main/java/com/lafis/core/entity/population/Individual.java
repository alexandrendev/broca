package com.lafis.core.entity.population;

import com.lafis.core.entity.interval.Interval;
import com.lafis.core.ports.RandomGenerator;

import java.util.List;

public class Individual {
    private int initialPopulation;
    private int currentPopulation;
    private final RandomGenerator random;


    public void applyEffect(Interval interval){
        if (currentPopulation == 0) return;

        switch (interval.getEffect()) {
            case DIE -> currentPopulation--;
            case LIVE -> currentPopulation++;
        }
    };

    public void monteCarlo(List<Interval> intervals) {
        double maxValue = intervals.getLast().getValue();

        for (int i = 0; i < currentPopulation; i++) {
            double rand = random.nextDouble() * maxValue;
            for (Interval interval : intervals) {
                if (rand <= interval.getValue()) {
                    applyEffect(interval);
                    break;
                }
            }
        }
    }


    public Individual(int initialPopulation, int currentPopulation, RandomGenerator random) {
        this.initialPopulation = initialPopulation;
        this.currentPopulation = currentPopulation;
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

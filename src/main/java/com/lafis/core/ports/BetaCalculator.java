package com.lafis.core.ports;

import com.lafis.core.entity.population.PopulationData;

public interface BetaCalculator {

    double calculateBeta(double beta, double maxTemperature, double temperature);
}

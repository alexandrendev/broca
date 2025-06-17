package com.lafis.core.entity.equation;

import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.PopulationData;

import java.util.List;

public interface Equation {

    List<Interval> getFirstLineIntervals(PopulationData data, double dailyBeta);
    List<Interval> getSecondLineIntervals(PopulationData data, double dailyBeta);
    List<Interval> getThirdLineIntervals(PopulationData data);

    double getBeta();

    void setBeta(double beta);
}

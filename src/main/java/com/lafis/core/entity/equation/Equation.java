package com.lafis.core.entity.equation;

import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.PopulationData;

import java.util.List;

public interface Equation {

    List<Interval> getFirstLineIntervals(PopulationData data);
    List<Interval> getSecondLineIntervals(PopulationData data);
    List<Interval> getThirdLineIntervals(PopulationData data);
}

package com.lafis.core.entity.equation;

import com.lafis.core.entity.interval.Effect;
import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.PopulationData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sakthivel implements Equation {
    private double beta, m1, m2, m3, n1, n2, n3, alpha, K;


    @Override
    public List<Interval> getFirstLineIntervals(PopulationData data, double dailyBeta) {
        int H = data.hosts();
        int I = data.infecteds();

        double term1 = dailyBeta * (1 - H / K) * H;
        double term2 = m1 * H;
        double term3 = n1 * H;
        double term4 = alpha * H * I;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));
        values.add(new Interval(term1 + term2 + term3, Effect.INFECTED));
        values.add(new Interval(term1 + term2 + term3 + term4, Effect.INFECTED));

        return normalizeAndSortIntervals(values);
    }

    @Override
    public List<Interval> getSecondLineIntervals(PopulationData data, double dailyBeta) {
        int H = data.hosts();
        int I = data.infecteds();

        double term1 = alpha * H * I;
        double term2 = m2 * I;
        double term3 = n2 * I;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));
        values.add(new Interval(term1 + term2 + term3, Effect.DEATH_DUE_TO_PARASITE));

        return normalizeAndSortIntervals(values);
    }

    @Override
    public List<Interval> getThirdLineIntervals(PopulationData data) {
        int H = data.hosts();
        int P = data.parasitoids();
        int I = data.infecteds();

        double term1 = n1 * I;
        double term2 = m3 * P;
        double term3 = n3 * P;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));
        values.add(new Interval(term1 + term2 + term3, Effect.NATURAL_DEATH));

        return normalizeAndSortIntervals(values);
    }


    private List<Interval> normalizeAndSortIntervals(List<Interval> intervals) {
        List<Double> limits = intervals.stream()
                .map(Interval::getValue)
                .collect(Collectors.toList());


        if (limits.isEmpty() || limits.stream().allMatch(value -> value == 0.0)) {
            return intervals.stream()
                    .map(interval -> new Interval(0.0, interval.getEffect()))
                    .collect(Collectors.toList());
        }


        double total = limits.stream().mapToDouble(Double::doubleValue).sum();

        if (total == 0.0) {
            return intervals.stream()
                    .map(interval -> new Interval(0.0, interval.getEffect()))
                    .collect(Collectors.toList());
        }


        return intervals.stream()
                .map(interval -> new Interval(interval.getValue() / total, interval.getEffect()))
                .collect(Collectors.toList());
    }


    @Override
    public double getBeta() {
        return beta;
    }

    @Override
    public void setBeta(double beta) {
        this.beta = beta;
    }

    public Sakthivel(double beta, double m1, double m2, double m3, double n1, double n2, double n3, double alpha, double k) {
        this.beta = beta;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.alpha = alpha;
        K = k;
    }
}

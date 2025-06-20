package com.lafis.core.entity.equation;

import com.lafis.core.entity.interval.Effect;
import com.lafis.core.entity.interval.Interval;
import com.lafis.core.entity.population.PopulationData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Rafikov implements Equation {
    public double n1, r, m2, n2, m3, K, gamma, beta;

    @Override
    public List<Interval> getFirstLineIntervals(PopulationData data, double dailyBeta) {
        int H = data.hosts();
        int P = data.parasitoids();

        double term1 = this.r * (1 - H / this.K) * H;
        double term2 = this.n1 * H;
        double term3 = dailyBeta * H * P;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));
        values.add(new Interval(term1 + term2 + term3, Effect.INFECTED));

        return normalizeInterval(values);
    }

    @Override
    public List<Interval> getSecondLineIntervals(PopulationData data, double dailyBeta) {
        int H = data.hosts();
        int I = data.infecteds();
        int P = data.parasitoids();

        double term1 = dailyBeta * H * P;
        double term2 = this.m2 * I;
        double term3 = this.n2 * I;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));
        values.add(new Interval(term1 + term2 + term3, Effect.DEATH_DUE_TO_PARASITE));

        return normalizeInterval(values);
    }

    @Override
    public List<Interval> getThirdLineIntervals(PopulationData data) {
        int I = data.infecteds();
        int P = data.parasitoids();

        double term1 = this.gamma * this.n2 * I;
        double term2 = this.m3 * P;

        List<Interval> values = new ArrayList<>();
        values.add(new Interval(term1, Effect.REPRODUCE));
        values.add(new Interval(term1 + term2, Effect.NATURAL_DEATH));

        return normalizeInterval(values);
    }


    private List<Interval> normalizeInterval(List<Interval> intervals) {
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

    public Rafikov(double n1, double r, double m2, double n2, double m3, double k, double gamma, double beta) {
        this.n1 = n1;
        this.r = r;
        this.m2 = m2;
        this.n2 = n2;
        this.m3 = m3;
        K = k;
        this.gamma = gamma;
        this.beta = beta;
    }

    public double getN1() {
        return n1;
    }

    public double getR() {
        return r;
    }

    public double getM2() {
        return m2;
    }

    public double getN2() {
        return n2;
    }

    public double getM3() {
        return m3;
    }

    public double getK() {
        return K;
    }

    public double getGamma() {
        return gamma;
    }

    public double getBeta() {
        return beta;
    }

    @Override
    public void setBeta(double beta) {
        this.beta = beta;
    }

}

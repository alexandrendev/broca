package com.lafis.core.entity.equation;

public class Rafikov implements Equation {
    public double n1, r, beta, m2, n2, m3, K, gamma;

    public Rafikov(double n1, double r, double beta, double m2, double n2, double m3, double k, double gamma) {
        this.n1 = n1;
        this.r = r;
        this.beta = beta;
        this.m2 = m2;
        this.n2 = n2;
        this.m3 = m3;
        K = k;
        this.gamma = gamma;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getM2() {
        return m2;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }

    public double getM3() {
        return m3;
    }

    public void setM3(double m3) {
        this.m3 = m3;
    }

    public double getK() {
        return K;
    }

    public void setK(double k) {
        K = k;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}

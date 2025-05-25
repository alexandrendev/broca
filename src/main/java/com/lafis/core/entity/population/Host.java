package com.lafis.core.entity.population;

public class Host {
    private int initialPopulation;
    private int currentPopulation;


    public Host(int initialPopulation) {
        this.initialPopulation = initialPopulation;
        this.currentPopulation = initialPopulation;
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

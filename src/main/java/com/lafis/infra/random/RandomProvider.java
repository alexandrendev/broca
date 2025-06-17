package com.lafis.infra.random;

import com.lafis.core.ports.RandomGenerator;

import java.util.Random;

public class RandomProvider implements RandomGenerator {
//    private Random random;

    @Override
    public double nextDouble() {
        return Math.random();
    }

    public RandomProvider() {

    }
}

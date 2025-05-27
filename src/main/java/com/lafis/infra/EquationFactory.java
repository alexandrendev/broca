package com.lafis.infra;

import com.lafis.core.entity.equation.Equation;
import com.lafis.core.entity.equation.Rafikov;

public class EquationFactory {

    public static Equation create(Equilibrium equilibrium){
        switch(equilibrium){
            case FIRST ->
            {
                return new Rafikov(0.15, 0.1, 0.036, 0.0625, 0.5, 25000, 40, 4.147e-07);
            }
            case SECOND ->
            {
                return new Rafikov(0.02, 0.025, 0.036, 0.0625, 0.5, 25000, 40, 1.9700e-06);
            }
            case THIRD ->
            {
                return new Rafikov(0.02, 0.025, 0.036, 0.0625, 0.5, 25000, 40, 4.1105e-06);
            }
            default -> throw new IllegalArgumentException("Invalid equilibrium");
        }
    }
}

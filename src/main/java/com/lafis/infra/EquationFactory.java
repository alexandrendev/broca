package com.lafis.infra;

import com.lafis.core.entity.equation.Equation;
import com.lafis.core.entity.equation.Rafikov;
import com.lafis.core.entity.equation.Sakthivel;

public class EquationFactory {

    public Equation create(AnalyticModel model, Equilibrium equilibrium){
        switch (model){
            case RAFIKOV ->{
                return rafikov(equilibrium);
            }
            case SAKTHIVEL-> {
                return sakthivel(equilibrium);
            }
            default -> throw new IllegalArgumentException("Unknown model");
        }
    }

    public Equation rafikov(Equilibrium equilibrium){
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
//                return new Rafikov(0.02, 0.025, 0.036, 0.0625, 0.5, 25000, 40, 2.1332e-05);
                return new Rafikov(0.02, 0.025, 0.036, 0.0625, 0.5, 25000, 40, 4.0e-06);
            }
            default -> throw new IllegalArgumentException("Invalid equilibrium");
        }
    }

    public Equation sakthivel(Equilibrium equilibrium){
        switch(equilibrium) {
            case FIRST -> {
                return new Sakthivel(0.11566, 0.03566, 0.03566, 0.00256, 0.1, 0.10000, 0.02439, 0.0001723, 25000);
            }
            case SECOND -> {
                return new Sakthivel(0.139, 0.03566, 0.03566, 0.00256, 0.1, 0.10000, 0.02439, 0.0001723, 25000);
            }
            case THIRD -> {
                return new Sakthivel(0.1908, 0.03566, 0.03566, 0.00256, 0.1, 0.10000, 0.02439, 0.0001723,25000);
            }
            default -> throw new IllegalArgumentException("Invalid equilibrium");
        }
    }
}

package com.lafis.infra;

public enum Equilibrium {
    FIRST(1, "first"),
    SECOND(2, "second"),
    THIRD(3, "third");

    final int num;
    final String name;

    Equilibrium(int num, String name) {
        this.num = num;
        this.name = name;
    }
}

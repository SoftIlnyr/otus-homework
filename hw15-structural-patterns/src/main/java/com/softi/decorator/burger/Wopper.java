package com.softi.decorator.burger;

public class Wopper extends Burger {

    public Wopper() {
        description = "Воппер";
    }

    @Override
    public double getCost() {
        return 120;
    }

    @Override
    protected double getCutletCost() {
        return 80;
    }
}

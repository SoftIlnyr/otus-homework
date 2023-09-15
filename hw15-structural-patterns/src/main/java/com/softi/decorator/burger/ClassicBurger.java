package com.softi.decorator.burger;

public class ClassicBurger extends Burger {

    public ClassicBurger() {
        description = "Классический бургер";
    }

    @Override
    public double getCost() {
        return 50;
    }

    @Override
    protected double getCutletCost() {
        return 40;
    }
}

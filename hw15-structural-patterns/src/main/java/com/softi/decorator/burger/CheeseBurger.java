package com.softi.decorator.burger;

public class CheeseBurger extends Burger {
    
    public CheeseBurger() {
        description = "Чизбургер";
    }

    @Override
    public double getCost() {
        return 60;
    }

    @Override
    protected double getCutletCost() {
        return 40;
    }
}

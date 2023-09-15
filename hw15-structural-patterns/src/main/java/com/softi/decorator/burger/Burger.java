package com.softi.decorator.burger;

import com.softi.decorator.Product;

public abstract class Burger extends Product {

    protected static final double BASE_CUTLET_COST = 40;

    protected String description;

    @Override
    public String getDescription() {
        return description;
    }
    
    protected double getCutletCost() {
        return BASE_CUTLET_COST;
    }

}

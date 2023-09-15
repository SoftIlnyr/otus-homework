package com.softi.decorator.burger;

public class ExtraCutletDecorator extends BurgerDecorator {

    public ExtraCutletDecorator(Burger burger) {
        super(burger);
        description = "с дополнительной котлетой";
    }

    @Override
    public double getCost() {
        return burger.getCost() + burger.getCutletCost();
    }
}

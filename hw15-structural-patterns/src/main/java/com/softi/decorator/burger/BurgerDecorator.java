package com.softi.decorator.burger;

public abstract class BurgerDecorator extends Burger{

    protected Burger burger;

    @Override
    public String getDescription() {
        return burger.getDescription() + ",\n" + this.description;
    }

    public BurgerDecorator(Burger burger) {
        this.burger = burger;
    }
}

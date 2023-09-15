package com.softi.decorator.burger;

public class ExtraCheeseDecorator extends BurgerDecorator {

    public ExtraCheeseDecorator(Burger burger) {
        super(burger);
        description = "с дополнительным сыром";
    }

    @Override
    public double getCost() {
        return burger.getCost() + 10;
    }
}

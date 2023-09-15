package com.softi.decorator.burger;

public class ExtraCucumberDecorator extends BurgerDecorator {

    public ExtraCucumberDecorator(Burger burger) {
        super(burger);
        description = "с дополнительной порцией огурчиков";
    }

    @Override
    public double getCost() {
        return burger.getCost() + 20;
    }
}

package com.softi.fruits;

public class Apple extends Fruit{
    
    public static final int AVERAGE_WEIGHT = 150;
    
    public Apple() {
        this.weight = AVERAGE_WEIGHT;
    }

    public Apple(int weight) {
        this.weight = weight;
    }
}

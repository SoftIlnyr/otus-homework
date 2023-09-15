package com.softi.fruits;

public class Orange extends Fruit {
    
    public static final int AVERAGE_WEIGHT = 200;

    public Orange() {
        this.weight = AVERAGE_WEIGHT;
    }

    public Orange(int weight) {
        this.weight = weight;
    }
}

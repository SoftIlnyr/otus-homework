package com.softi.fruits;

import java.util.ArrayList;
import java.util.List;

public class FruitBox<T extends Fruit>{

    private List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
    
    public int weight() {
        int weight = fruits.stream()
                .mapToInt(Fruit::getWeight)
                .sum();

        return weight;
    }

    public void pourInto(FruitBox<? super T> fruitBox) {
        if (this == fruitBox || fruitBox == null) {
            return;
        }
        fruitBox.fruits.addAll(this.fruits);
        this.fruits.clear();
    }

    public boolean compare(FruitBox<? extends Fruit> o) {
        return this.weight() == o.weight();
    }
}

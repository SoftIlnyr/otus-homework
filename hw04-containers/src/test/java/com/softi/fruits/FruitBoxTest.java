package com.softi.fruits;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FruitBoxTest {

    @Test
    public void add() {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        int customAppleWeight = 50;
        fruitBox.add(new Apple(customAppleWeight));

        assertEquals(customAppleWeight, fruitBox.weight());

        int customOrangeWeight = 80;
        fruitBox.add(new Orange(customOrangeWeight));

        assertEquals(customAppleWeight + customOrangeWeight, fruitBox.weight());
    }

    @ParameterizedTest
    @MethodSource("weight")
    public void weight(int fruitAppleCount, int fruitOrangeCount, int appleCount, int orangeCount) {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        for (int i = 0; i < fruitAppleCount; i++) {
            fruitBox.add(new Apple());
        }
        for (int i = 0; i < fruitOrangeCount; i++) {
            fruitBox.add(new Orange());
        }
        assertEquals(Apple.AVERAGE_WEIGHT * fruitAppleCount + Orange.AVERAGE_WEIGHT * fruitOrangeCount,
                fruitBox.weight());

        FruitBox<Apple> appleBox = new FruitBox<>();
        for (int i = 0; i < appleCount; i++) {
            appleBox.add(new Apple());
        }

        assertEquals(Apple.AVERAGE_WEIGHT * appleCount, appleBox.weight());

        FruitBox<Orange> orangeBox = new FruitBox<>();
        for (int i = 0; i < orangeCount; i++) {
            orangeBox.add(new Orange());
        }

        assertEquals(Orange.AVERAGE_WEIGHT * orangeCount, orangeBox.weight());

        appleBox.pourInto(fruitBox);

        assertEquals(0, appleBox.weight());
        assertEquals(Apple.AVERAGE_WEIGHT * (fruitAppleCount + appleCount)
                + Orange.AVERAGE_WEIGHT * (fruitOrangeCount), fruitBox.weight());

        orangeBox.pourInto(fruitBox);

        assertEquals(0, orangeBox.weight());
        assertEquals(Apple.AVERAGE_WEIGHT * (fruitAppleCount + appleCount)
                + Orange.AVERAGE_WEIGHT * (fruitOrangeCount + orangeCount), fruitBox.weight());
    }

    static Stream<Arguments> weight() {
        return Stream.of(
                Arguments.of(0, 0, 2, 2),
                Arguments.of(5, 5, 0, 0),
                Arguments.of(5, 0, 5, 0),
                Arguments.of(1, 1, 1, 1)
        );
    }

    @Test
    public void compare() {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        FruitBox<Apple> appleBox = new FruitBox<>();
        FruitBox<Orange> orangeBox = new FruitBox<>();

        int customAppleWeight = 50;
        int customOrangeWeight = 80;
        fruitBox.add(new Apple(customAppleWeight));

        appleBox.add(new Apple(customAppleWeight));

        assertTrue(fruitBox.compare(appleBox));

        orangeBox.add(new Orange(customOrangeWeight));

        assertFalse(orangeBox.compare(appleBox));

    }
    
    @Test
    public void pour() {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        fruitBox.add(new Apple());
        fruitBox.add(new Apple());
        fruitBox.add(new Orange());
        
        fruitBox.pourInto(fruitBox);
        fruitBox.pourInto(null);
        
        assertEquals(Apple.AVERAGE_WEIGHT * 2 + Orange.AVERAGE_WEIGHT, fruitBox.weight());
    }

}
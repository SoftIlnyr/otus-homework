package com.softi.reflections;

import com.softi.fruits.Apple;
import com.softi.fruits.Fruit;
import com.softi.fruits.FruitBox;
import com.softi.fruits.Orange;

public class FruitBoxCustomTest {

    private FruitBox<Fruit> fruitBox;
    private FruitBox<Apple> appleBox;
    private FruitBox<Orange> orangeBox;
    
    @Before
    public void setUp() {
        System.out.println("Calling before method");
        fruitBox = new FruitBox<>();
        appleBox = new FruitBox<>();
        orangeBox = new FruitBox<>();
    }
    
    @Test
    public void testFruits() {
        int customAppleWeight = 50;
        fruitBox.add(new Apple(customAppleWeight));
        System.out.println("Fruit box weight is " + fruitBox.weight());
    }
    
    @Test
    public void testApple() {
        appleBox.add(new Apple());
        System.out.println("Apple box weight is " + appleBox.weight());
    }
    
    @After
    public void tearDown() {
        System.out.println("Clearing data");
        fruitBox = new FruitBox<>();
        appleBox = new FruitBox<>();
        orangeBox = new FruitBox<>();
    }
    
    
    
}

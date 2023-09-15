package com.softi.reflections;

import com.softi.fruits.Apple;
import com.softi.fruits.Fruit;
import com.softi.fruits.FruitBox;
import com.softi.fruits.Orange;

public class FruitBoxCustomTest2 {

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
    public void testPour() {
        fruitBox.add(new Apple());
        fruitBox.add(new Apple());

        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        
        orangeBox.pourInto(fruitBox);
        
        System.out.println("Fruit box weight is " + fruitBox.weight());
    }
    
    @Test
    public void testException() throws Exception {
        throw new Exception("Need to test failed tests");
    }
    
    @After
    public void tearDown() {
        System.out.println("Clearing data");
        fruitBox = new FruitBox<>();
        appleBox = new FruitBox<>();
        orangeBox = new FruitBox<>();
    }
    
    
    
}

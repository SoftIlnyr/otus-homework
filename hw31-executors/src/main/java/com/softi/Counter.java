package com.softi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {

    private static final Logger logger = LoggerFactory.getLogger(Counter.class);

    private final int MIN_VALUE;
    private final int MAX_VALUE;

    private boolean positionDirection = true;

    private int currentValue;

    public Counter() {
        this(0, 10);
    }

    public Counter(int min_value, int max_value) {
        this.MIN_VALUE = min_value;
        this.MAX_VALUE = max_value;
        this.currentValue = min_value;
    }

    public void count() {
        while (!Thread.currentThread().isInterrupted()) {
            changeValue();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void changeValue() {
        logger.info("Thread {}. Current value: {}", Thread.currentThread().getName(), currentValue);
        if (positionDirection) {
            if (currentValue >= MAX_VALUE) {
                positionDirection = false;
                currentValue--;
            } else {
                currentValue++;
            }
        } else {
            if (currentValue <= MIN_VALUE) {
                positionDirection = true;
                currentValue++;
            } else {
                currentValue--;
            }
        }
    }

    public static void main(String[] args) {
        new Counter(0, 1).count();

//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//        executorService.submit(() -> new Counter(5, 10).count());
//        executorService.submit(() -> new Counter(0, 10).count());
//        executorService.submit(() -> new Counter(10, 20).count());
    }

}

package com.softi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongCounter {

    private static final Logger logger = LoggerFactory.getLogger(PingPongCounter.class);

    private final int MIN_VALUE;
    private final int MAX_VALUE;

    private boolean positionDirection = true;

    private int currentValue;

    private static List<String> threadNames;

    private static String currentThreadName;
    private static Integer currentThreadIndex;

    public PingPongCounter() {
        this(0, 10);
    }

    public PingPongCounter(int min_value, int max_value) {
        this.MIN_VALUE = min_value;
        this.MAX_VALUE = max_value;
        this.currentValue = min_value;
    }

    public synchronized void count() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                logger.info("Waiting for thread {}", currentThreadName);

                while (true) {
                    String currentThreadName = Thread.currentThread().getName();
                    if (!currentThreadName.equals(PingPongCounter.currentThreadName))
                        break;
                    logger.info("Thread {} is waiting", currentThreadName);
                    this.wait();
                }
                logger.info("Entered thread {}", currentThreadName);

                changeValue();

                currentThreadIndex = (currentThreadIndex + 1) % threadNames.size();
                currentThreadName = threadNames.get(currentThreadIndex);
                logger.info("Set new thread {}", currentThreadName);

                notifyAll();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void changeValue() {
        if (positionDirection) {
            if (currentValue >= MAX_VALUE) {
                positionDirection = false;
                currentValue--;
            }
            currentValue++;
        } else {
            if (currentValue <= MIN_VALUE) {
                positionDirection = true;
                currentValue++;
            }
            currentValue--;
        }
        logger.info("Thread {}. Current value: {}", Thread.currentThread().getName(), currentValue);
    }


    public static void main(String[] args) {
        PingPongCounter counter1 = new PingPongCounter(0, 10);
        PingPongCounter counter2 = new PingPongCounter(0, 10);

        pingPongCount(counter1, counter2);
    }

    private static void pingPongCount(PingPongCounter... counters) {

        List<Thread> threads = Arrays.stream(counters).map(counter -> new Thread(counter::count)).toList();

        threadNames = threads.stream().map(Thread::getName).collect(Collectors.toList());
        currentThreadIndex = 0;
        currentThreadName = threads.get(currentThreadIndex).getName();

        for (Thread thread : threads) {
            thread.start();
        }
    }

}

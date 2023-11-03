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

    private final List<Counter> counters;
    private  List<String> threadNames;
    private String currentThreadName;
    private Integer currentThreadIndex;

    public PingPongCounter(Counter... counters) {
        this.counters = Arrays.asList(counters);
    }

    private synchronized void pingPong(Counter counter) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!Thread.currentThread().getName().equals(currentThreadName)) {
                    this.wait();
                }

                counter.changeValue();

                currentThreadIndex = (currentThreadIndex + 1) % threadNames.size();
                currentThreadName = threadNames.get(currentThreadIndex);

                notifyAll();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void count() {

    }


    public static void main(String[] args) {
        Counter counter1 = new Counter(1, 10);
        Counter counter2 = new Counter(1, 10);
        Counter counter3 = new Counter(1, 10);
        Counter counter4 = new Counter(1, 10);
        PingPongCounter pingPongCounter = new PingPongCounter(counter1, counter2, counter3, counter4);
        pingPongCounter.startCounting();

    }

    private void startCounting() {
        List<Thread> threads = counters.stream().map(counter -> new Thread(() -> pingPong(counter))).toList();

        threadNames = threads.stream().map(Thread::getName).collect(Collectors.toList());
        currentThreadIndex = 0;
        currentThreadName = threads.get(currentThreadIndex).getName();

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

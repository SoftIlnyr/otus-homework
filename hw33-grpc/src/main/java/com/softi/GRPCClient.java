package com.softi;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import ru.otus.protobuf.generated.CountdownMessage;
import ru.otus.protobuf.generated.CountingServiceGrpc;
import ru.otus.protobuf.generated.StartCountingMessage;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var latch = new CountDownLatch(2);
        var stub = CountingServiceGrpc.newStub(channel);

        long firstValue = 1;
        long lastValue = 100;

        BlockingDeque<CountdownMessage> queue = new LinkedBlockingDeque<>();

        var initMessage = StartCountingMessage.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();

        stub.initCounting(initMessage, new StreamObserver<>() {
            @Override
            public void onNext(CountdownMessage value) {
                queue.add(value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        new Thread(() -> {
            long currentValue = 0;
            long currentServerValue = firstValue;
            while (currentServerValue < lastValue) {
                try {
                    Thread.sleep(1000);
                    CountdownMessage countdownMessage = queue.pollLast();
                    if (countdownMessage != null) {
                        currentServerValue = countdownMessage.getCurrentServerValue();
                        System.out.println("------------------------------");
                        System.out.println("Server: " + currentServerValue);
                        currentValue = currentValue + currentServerValue + 1;
                    } else {
                        currentValue = currentValue + 1;
                    }
                    System.out.println("Client: " + currentValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }).start();

        latch.await();

        channel.shutdown();

    }

}

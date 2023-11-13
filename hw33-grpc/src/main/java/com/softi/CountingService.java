package com.softi;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import ru.otus.protobuf.generated.CountdownMessage;
import ru.otus.protobuf.generated.CountingServiceGrpc;
import ru.otus.protobuf.generated.StartCountingMessage;

public class CountingService extends CountingServiceGrpc.CountingServiceImplBase {

    private final ScheduledExecutorService scheduledExecutor;

    public CountingService() {
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void initCounting(StartCountingMessage request, StreamObserver<CountdownMessage> responseObserver) {
        long firstValue = request.getFirstValue();
        long lastValue = request.getLastValue();

        for (long currentValue = firstValue; currentValue <= lastValue; currentValue++) {
            try {
                int sleepTime = (int) (Math.random() * 1000 * 5);
                System.out.println("Sleep time: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sending current value " + currentValue);
            CountdownMessage countDownMessage = CountdownMessage.newBuilder()
                    .setCurrentServerValue(currentValue)
                    .build();
            responseObserver.onNext(countDownMessage);
        }

        responseObserver.onCompleted();
    }
}

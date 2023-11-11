package com.softi;

import io.grpc.ServerBuilder;
import java.io.IOException;

public class GRPCServer {
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var countingService = new CountingService();

        var server = ServerBuilder.forPort(SERVER_PORT)
                .addService(countingService)
                .build();

        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}

package com.example.concurrency.features.completablefuture3;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleNonBlocking {
    private static final Logger log = LoggerFactory.getLogger(SimpleNonBlocking.class);

    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "Async Task")
                .thenAccept(s -> log.info("future: {}", s));
        future.join(); // Wait for the task to finish (non-blocking)
    }
}

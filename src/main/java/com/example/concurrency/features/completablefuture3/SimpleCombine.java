package com.example.concurrency.features.completablefuture3;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.example.concurrency.util.ThreadUtil.sleep;

public class SimpleCombine {
    private static final Logger log = LoggerFactory.getLogger(SimpleCombine.class);

    public static void main(String[] args) {
        // Wait for all the futures to complete.
        CompletableFuture<Void> future = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> log.info("Task 1")),
                CompletableFuture.runAsync(() -> log.info("Task 2")),
                CompletableFuture.runAsync(() -> log.info("Task 3"))
        );

        // Wait for any one of the futures to complete.
        CompletableFuture<Object> future1  = CompletableFuture.anyOf(
                CompletableFuture.runAsync(() -> { sleep(5, TimeUnit.SECONDS); log.info("Task 1");}),
                CompletableFuture.runAsync(() -> { sleep(9, TimeUnit.SECONDS); log.info("Task 2");}),
                CompletableFuture.runAsync(() -> { sleep(2, TimeUnit.SECONDS); log.info("Task 3");})
        ).exceptionally(ex -> {
            log.error("Exception occurred: {}", ex.getMessage());
            return null;
        });

        // Get the CompletableFuture's result
        future.join();
        future1.join();
    }
}

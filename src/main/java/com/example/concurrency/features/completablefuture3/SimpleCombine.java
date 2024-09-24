package com.example.concurrency.features.completablefuture3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
                CompletableFuture.runAsync(() -> { sleep(10, TimeUnit.SECONDS); log.info("Task 4");}),
                CompletableFuture.runAsync(() -> { sleep(5, TimeUnit.SECONDS); log.info("Task 5");}),
                CompletableFuture.runAsync(() -> { sleep(1, TimeUnit.SECONDS); log.info("Task 6");})
        ).exceptionally(ex -> {
            log.error("Exception occurred in future1: {}", ex.getMessage());
            return null;
        });

        CompletionStage<Object> future2 = CompletableFuture.anyOf(
                CompletableFuture.runAsync(() -> { sleep(3, TimeUnit.SECONDS); log.info("Task X");}),
                CompletableFuture.runAsync(() -> { sleep(2, TimeUnit.SECONDS); log.info("Task Y");}),
                CompletableFuture.runAsync(() -> { sleep(1, TimeUnit.SECONDS); log.info("Task Z");})
        ).exceptionally(ex -> {
                log.error("Exception occurred in future2: {}", ex.getMessage());
                return null;
        }).handle((result, exp) -> {
            if (exp != null) {
                log.error("Exception occurred when handle in future2: {}", exp.getMessage());
            }
            return result;
        });

        // Get the CompletableFuture's result
        future.join();
        future1.join();
        future2.toCompletableFuture().join();

    }
}

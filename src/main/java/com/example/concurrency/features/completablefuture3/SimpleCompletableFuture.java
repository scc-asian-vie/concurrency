package com.example.concurrency.features.completablefuture3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleCompletableFuture {

    private static final Logger log = LoggerFactory.getLogger(SimpleFuture.class);

    public static void main(String[] args) {

        // Transforms the result of the future when it completes.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s + " world!");

        // Chains another CompletableFuture that depends on the result of the first.
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " world!"));

        // Combine results of two futures when both are completed.
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> "world!"), (s1, s2) -> s1 + " " + s2);
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> "world!");
        CompletableFuture<String> combineFuture = future4.thenCombine(future5, (s1,s2) -> s1 + " " + s2);

        // Get the CompletableFuture's result
        try {
            log.info(future.get());
            log.info(future2.get());
            log.info(future3.get());
            log.info(combineFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

}

package com.example.concurrency.features.completablefuture3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleFuture {

    private static final Logger log = LoggerFactory.getLogger(SimpleFuture.class);

    public static void main(String[] args) {

        // Create a CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();

        // Get the CompletableFuture's CompletableFuture
        CompletableFuture<String> future2 = future.thenApply(s -> {
            log.info("future2: {}", s);
            return s.toUpperCase();
        });

        // Get the CompletableFuture's CompletableFuture
        CompletableFuture<String> future3 = future2.thenApply(s -> {
            log.info("future3: {}" , s);
            return s + "!";
        });

        // Complete the CompletableFuture
        future.complete("Hello");

        // Get the CompletableFuture's result
        try {
            log.info(future.get());
            log.info(future2.get());
            log.info(future3.get());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }
}

package com.example.concurrency.features.completablefuture3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleHandleException {

    private static final Logger log = LoggerFactory.getLogger(SimpleHandleException.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Handle exceptions that occur during the asynchronous computation.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new IllegalArgumentException("Exception occurred");
            }
            return "Hello";
        }).exceptionally(ex -> "Exception: " + ex.getMessage());

        // Handles both the result and the exception, offering more control over outcomes.
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new IllegalArgumentException("Exception occurred");
            }
            return "Hello";
        }).handle((result, ex) -> ex != null ? "Handled exception: " + ex.getMessage() : result);

        // Get the CompletableFuture's result
        future.get();
        future2.get();
    }
}

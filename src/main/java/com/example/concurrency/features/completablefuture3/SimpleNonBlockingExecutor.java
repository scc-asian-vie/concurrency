package com.example.concurrency.features.completablefuture3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleNonBlockingExecutor {
    private static final Logger log = LoggerFactory.getLogger(SimpleNonBlockingExecutor.class);

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException, CompletionException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApplyAsync(s -> s + " World", executor);
        // Wait for the task to finish (non-blocking) using get ( might throw an checked exception)
        log.info(future.get());
        // Wait for the task to finish (non-blocking) using join ( might throw an unchecked exception)
        String result = future.join();
        log.info(result);
        executor.shutdown();
    }

}

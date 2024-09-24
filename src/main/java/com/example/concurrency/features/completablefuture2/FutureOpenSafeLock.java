package com.example.concurrency.features.completablefuture2;

import com.example.concurrency.features.completablefuture2.Items.Actions;
import com.example.concurrency.features.completablefuture2.Items.Loot;
import com.example.concurrency.features.completablefuture2.Items.Thief;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureOpenSafeLock {

    private static final Logger log = LoggerFactory.getLogger(FutureOpenSafeLock.class);

    public Loot openSafeLock(final Thief thief, final String victim) throws InterruptedException, ExecutionException {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        final Future<Boolean> doorUnlockFuture = executor.submit(Actions::unlockTheDoor);
        // force to open the door of victim
        doorUnlockFuture.get();

        final Future<String> safetyBoxNumberFuture = executor.submit(
            () -> Actions.figureOutSafetyBoxNumber(victim)
        );
        final Future<Integer> pinRetreiverFuture = executor.submit(
            () -> Actions.hackSecretPin(victim)
        );
        final Future<Loot> lootFuture = executor.submit(
            () -> Actions.openSafeLock(safetyBoxNumberFuture.get(), pinRetreiverFuture.get())
        );
        executor.shutdown();
        final Loot loot = lootFuture.get();
        log.info("{} gets the content of the safety box: {}", thief.getName(), loot);
        return loot;
    }

    public static void main(String[] args) {
        final FutureOpenSafeLock futureOpenSafeLock = new FutureOpenSafeLock();
        final Thief thief = Thief.PETE;
        final String victim = "Alice";
        log.info("Thief {} is trying to open the safety box of {}", thief.getName(), victim);
        for(int counter=0;counter<10;counter++) {
            try {
                log.info("trying to break to Alice house...{}", counter);
                final Loot loot = futureOpenSafeLock.openSafeLock(thief, victim);
                log.info("Loot: {}", loot.getMsg());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Something went wrong: {} Run, run, run!!", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}

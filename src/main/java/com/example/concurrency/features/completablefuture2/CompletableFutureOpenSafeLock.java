package com.example.concurrency.features.completablefuture2;

import com.example.concurrency.features.completablefuture2.Items.Actions;
import com.example.concurrency.features.completablefuture2.Items.Loot;
import com.example.concurrency.features.completablefuture2.Items.Thief;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureOpenSafeLock {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureOpenSafeLock.class);

    public static Loot openSafeLock(final Thief thief, final String victim) {
        return CompletableFuture.supplyAsync(Actions::unlockTheDoor)
                .thenCompose(isOpened ->
                        CompletableFuture.supplyAsync(() -> Actions.figureOutSafetyBoxNumber(victim))
                                .thenCombineAsync(
                                        CompletableFuture.supplyAsync(() -> Actions.hackSecretPin(victim)),
                                        Actions::openSafeLock
                                ).exceptionally(e -> {
                                            log.error("Something went wrong: {} Run, run, run!!", e.getMessage());
                                            return Loot.BAD;
                                        }
                                )
                ).thenApply(
                        loot -> {
                            log.info("{} gets the content of the safety box: '{}'", thief.getName(), thief.handleLoot(loot));
                            return loot;
                        }
                ).join();
    }

    public static void main(String[] args) {
        Thief thief = Thief.PETE;
        String victim = "Alice";
        log.info("Thief {} is trying to open the safety box of {}", thief.getName(), victim);
        Loot loot = openSafeLock(thief, victim);
        log.info("Loot: {}", loot.getMsg());
    }

}

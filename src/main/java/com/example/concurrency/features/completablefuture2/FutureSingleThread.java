package com.example.concurrency.features.completablefuture2;

import com.example.concurrency.features.completablefuture2.Items.Actions;
import com.example.concurrency.features.completablefuture2.Items.Loot;
import com.example.concurrency.features.completablefuture2.Items.Thief;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureSingleThread {
    private static final Logger log = LoggerFactory.getLogger(FutureSingleThread.class);

    private Loot openSafeLockFunctional(final Thief thief, final String victim) {
        return Stream.of(Actions.unlockTheDoor())
            .map(isOpened -> Actions.figureOutSafetyBoxNumber(victim))
            .map(safetyBoxNumber -> Actions.openSafeLock(safetyBoxNumber, Actions.hackSecretPin(victim)))
            .findFirst().orElseGet(() -> Loot.BAD);
    }

    public static void main(String[] args) {
        final FutureSingleThread futureSingleThread = new FutureSingleThread();
        final Thief thief = Thief.PETE;
        final String victim = "Alice";
        log.info("Thief {} is trying to open the safety box of {}", thief.getName(), victim);
        for (int counter = 0; counter < 10; counter++) {
            try {
                log.info("trying to break to Alice house...{}", counter);
                final Loot loot = futureSingleThread.openSafeLockFunctional(thief, victim);
                log.info("Loot: {}", loot.getMsg());
            } catch (Exception e) {
                log.error("Something went wrong: {} Run, run, run!!", e.getMessage());
            }
        }
    }

}

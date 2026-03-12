package proj.food.vista.implementation.fx;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FxRuntime {

    private static final AtomicBoolean STARTED = new AtomicBoolean(false);
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    private FxRuntime() {
    }

    public static void runOnFxThread(Runnable action) {
        ensureStarted();
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }
        Platform.runLater(action);
    }

    private static void ensureStarted() {
        if (STARTED.compareAndSet(false, true)) {
            Platform.startup(LATCH::countDown);
        }
        awaitStartup();
    }

    private static void awaitStartup() {
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("JavaFX runtime startup interrupted", e);
        }
    }
}


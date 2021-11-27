package net.intelie.challenges;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EventThreadTest {

    @Test
    public void shouldInsertTenThreadsOfEventInserts() {
        EventStore eventStore = new EventStoreImpl();
        int quantityThreadsByType = 10;
        List<String> eventNames = Arrays.asList("CHROME", "INTELIJ", "OTHER");
        for (String eventName : eventNames) {
            ExecutorService executor = Executors.newFixedThreadPool(quantityThreadsByType);
            for (int i = 0; i < quantityThreadsByType; i++) {
                EventThread eventThread = new EventThread(eventStore, eventName, i);
                Thread t = new Thread(eventThread);
                executor.execute(t);
            }
            executor.shutdown();
            try {
                if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }
}
package net.intelie.challenges;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EventStore eventStore = new EventStoreImpl();
        List<String> events =  Arrays.asList("CHROME", "INTELIJ", "OTHER");
        for (int i = 0; i < 10; i++) {
            long finalI = i;
            events.forEach(e ->  new EventThread(eventStore, e, finalI).run());
        }
    }
}

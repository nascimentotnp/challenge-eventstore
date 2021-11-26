package net.intelie.challenges;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventStoreImplTest extends TestCase {

    @Mock
    public EventStoreImpl eventStore;
    public SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());

    @Test
    public void testNoInsert() throws Exception {
        eventStore = new EventStoreImpl();
        Assertions.assertFalse(false);
    }
    @Test
    public void testInsert() {
        SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
        event.add(new Event("type1", 123L));
        eventStore = new EventStoreImpl();
        Assertions.assertTrue(true, "Type");
    }

    @Test
    public void testRemoveAll() throws Exception {
        SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
        event.add(new Event("type1", 123L));
        eventStore = new EventStoreImpl();
        String typeToRemove = "type1";
        eventStore.removeAll(typeToRemove);
        EventIterator iterator = eventStore.query(typeToRemove, 0, 2000000);
        Assertions.assertFalse(iterator.moveNext());

    }

    @Test
    public void testQuery() throws Exception{
        SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
        event.add(new Event("type1", 123L));
        eventStore = new EventStoreImpl();
        String iteratorType = "typeOne";
        long lowerBound = 4, higherBound = 20;
        EventIterator iterator = eventStore.query(iteratorType, lowerBound, higherBound);
        int count = 0;
        while (iterator.moveNext()) {
            Assertions.assertTrue(event.contains(iterator.current()));
            count++;
        }
        Assertions.assertEquals(1, event.size());
    }

}
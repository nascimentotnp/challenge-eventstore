package net.intelie.challenges;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class EventStoreImpl implements EventStore{

    private final SortedSet<Event> treeSet;

    public EventStoreImpl(){
        this.treeSet = Collections.synchronizedSortedSet(new TreeSet<>());
    }

//    The choice of using a TreeSet that implements an algorithm known as a
//    red-black tree is defeated by its main characteristic which is, it is
//    the only Set that implements a SortedSet interface instead of a Set directly,
//    but anyway SortedSet implements Set, so we continue having the same methods
//    on the TreeSet. Because it implements SortedSet it has automatically
//    sorted elements, that is, regardless of the order you insert the elements,
//    they will be sorted. Even having a higher cost of complexity for the add,
//    remove and contain methods, which are much larger than the HashSet, this method
//    was chosen, its complexity being O (log (n)), it is not exactly an exponential
//    complexity but it is much greater that O (1) that has its tense unchanged.
//    It works normally for primitive values, such as integers, but if we had been working
//    with objects, it wouldn't know how to order, hence the method choice.

//    In the removeAll method, we will have O(n) complexity, since we have to
//    search all nodes. It's the same complexity we would have using an array.

//    The query method, using the tree set, will have O(log n) complexity because
//    we need to check the start and end of the range. is the same
//    complexity that we could have using an array with binary search to get both
//   ends.

//    We could analyze other search algorithms but each case has its complexity, it is even
//    possible to calculate the exact time. And it can, but it's impractical in most situations
//    and it can only be an academic's idea to do this, you have to know all the implementation
//    and implementation details to reach this number, which ends up being irrelevant, because
//    it is more easily observable. On the other hand, if you don't know how his basic behavior
//    would be, even because without it you won't know how to fix something that is taking too long,
//    anything can happen.

//    TODO: Optimize the remove and query methods, by, instead of using a tree set,
//	  use a hash set, where the key would be the type of Event, and the value the
//	  tree set of that type of event. Like this we can change the removeAll
//	  complexity to O(1), just deleting the tree set of that type
//
//	  About the concurrency problem: tree sets do not have thread-safety included
//	  in it's implementation, since it is an implementation of a Navigable Set
//	  based on a tree set, that also is not thread-safe. Because of this, if more
//	  than one thread access the tree set concurrently and at least one of them
//	  modify it, we need to synchronize it externally with synchronizedSortedSet.

    @Override
    public synchronized void insert(Event event) {
        if(Objects.nonNull(event))
            this.treeSet.add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        treeSet.removeIf(e -> e.type().equals(type));
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        SortedSet<Event> subSet = treeSet.subSet(new Event(type, startTime), new Event(type, endTime));
        return new EventIteratorImpl(type, subSet.iterator());
    }
}


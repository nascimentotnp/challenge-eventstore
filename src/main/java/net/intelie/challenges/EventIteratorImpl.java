package net.intelie.challenges;

import java.util.Iterator;
public class EventIteratorImpl implements EventIterator{

    private boolean moveNext = false;
    private Event currentEvent;
    private final String type;
    private final Iterator<Event> iterator;


//  The isolated sequential control flow  within a program and the concurrent
//  multiple threads of execution in a single program, performing several
//  tasks “at the same” time, can generate a problem, different threads can run
//  on different processors, if available, or share a single processor and different
//  threads in the same program share a global environment (memory, processor, registers, etc.),
//  this can lead to complexity.
//  Each thread has a run() method that sets the concurrent activity, the concurrent activity
//  starts when the start() method on an object.
//  Runnable Interface is Used when you cannot inherit from the Thread class, as there is a need
//  to inherit from some other class.

//  important issues in Multithreading
//  Performance: the change of context of the different threads causes fall of performance (overhead).

//  Security: how to synchronize threads so they don't interfere with another Longevity: how to avoid
//  deadlock situations or livelock to ensure that all threads will do progress.

//   About concurrency: We can have threads creating multiple iterators for the
//	 same tree set. That could also be a problem, since they would access, being
//	 able to read, write or modify the data structure in memory simultaneously.
//	 Again, we do not want it to happen, so we need to synchronize it externally
//	 since tree sets aren't thread-safe. With that in mind, we know our moveNext,
//	 current and remove methods will work, without conflicts and returning
//	 expected results, without threads interfering with each other (one example
//	 would be two threads without locks calling moveNext at the same time and the
//	 iterator moving only once, since we have the read-write-modify process)

    public EventIteratorImpl(String type, Iterator<Event> eventIterator) {
        this.type = type;
        this.iterator = eventIterator;
    }

    @Override
    public boolean moveNext() {
        while (iterator.hasNext()) {
            this.currentEvent = iterator.next();
            if (this.currentEvent.type().equals(type)) {
                this.moveNext = true;
                return true;
            }
        }
        this.moveNext = false;
        return false;
    }

    @Override
    public synchronized Event current() {
        if(moveNext) return this.currentEvent;
        throw new IllegalStateException("Next event not called or not found");
    }

    @Override
    public synchronized void remove() {
        if(!moveNext) throw new IllegalStateException("Event to remove not found");
        iterator.remove();
    }

    @Override
    public void close() throws Exception {
    }
}

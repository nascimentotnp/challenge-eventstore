package net.intelie.challenges;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventIteratorTest {

	SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
	EventIterator iteratorTree = new EventIteratorImpl("type1", null);

	@Test
	public void testMoveNextWithoutEvents() {
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		Assert.assertFalse(iteratorTree.moveNext());
	}

	@Test
	public void testMoveNextCorrectType() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		Assert.assertTrue(iteratorTree.moveNext());
	}

	@Test
	public void testMoveNextEventsWithoutFindType() {
		iteratorTree = new EventIteratorImpl("type2", event.iterator());
		Assert.assertFalse(iteratorTree.moveNext());
	}

	@Test
	public void testMoveNexTrueWithoutMoreEvents() {
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		for (int i = 0; i < event.size() - 1; i++) {
			iteratorTree.moveNext();
		}
		Assert.assertFalse(iteratorTree.moveNext());
	}

	@Test
	public void testMoveNextIsFalse() {
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		while (iteratorTree.moveNext()) {
			Assert.assertTrue(iteratorTree.moveNext());
		}
	}

	@Test
	public void testCurrentEmptySet() {
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		assertThrows(IllegalStateException.class, iteratorTree::current);
	}

	@Test
	public void testCurrentSetOneNoMoveNext() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		EventIterator eventIteratorResult = new EventIteratorImpl("Type2",event.iterator());
		assertThrows(IllegalStateException.class, eventIteratorResult::current);
	}

	@Test
	public void testCurrentSetOneWithoutMoveNext() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		event.add(new Event("type2", 1234L));
		EventIterator eventIteratorResult = new EventIteratorImpl("type1", event.iterator());
		eventIteratorResult.moveNext();
		Assert.assertNotNull(eventIteratorResult.current());
	}

	@Test
	public void testCurrentSetOneWithNotFoundMoveNext() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		EventIterator eventIteratorResult = new EventIteratorImpl("Type1",event.iterator());
		eventIteratorResult.moveNext();
		Assert.assertFalse(eventIteratorResult.moveNext());
	}


	@Test
	public void testRemoveEmptySet() {
		iteratorTree = new EventIteratorImpl("type1", event.iterator());
		assertThrows(IllegalStateException.class, () -> iteratorTree.remove());
	}

	@Test
	public void testRemoveOneNoMoreMoveNext() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		EventIterator eventIteratorResult = new EventIteratorImpl("Type2",event.iterator());
		assertThrows(IllegalStateException.class, eventIteratorResult::remove);
	}

	@Test
	public void testRemoveEventSuccess() {
		SortedSet<Event> event = Collections.synchronizedSortedSet(new TreeSet<>());
		event.add(new Event("type1", 123L));
		EventIterator eventIteratorResult = new EventIteratorImpl("type1",event.iterator());
		Assert.assertTrue(eventIteratorResult.moveNext());
		eventIteratorResult.remove();
		Assert.assertFalse(eventIteratorResult.moveNext());
	}



}

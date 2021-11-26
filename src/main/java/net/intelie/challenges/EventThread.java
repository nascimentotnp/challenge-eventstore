package net.intelie.challenges;

/**
 * Runnable class for inserting an event into the store
 *
 */
public class EventThread implements Runnable{
	EventStore eventStore;
	String eventType;
	long timestamp;

	EventThread(EventStore eventStore, String eventType, long timestamp){
		this.eventStore=eventStore;
		this.eventType=eventType;
		this.timestamp=timestamp;
	}

	@Override
	public void run() {
		eventStore.insert(new Event(eventType, timestamp));
		System.out.println("Inserted eventType: " + eventType + " ---- timestamp: " + timestamp);
	}

}

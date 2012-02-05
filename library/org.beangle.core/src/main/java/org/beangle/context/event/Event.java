package org.beangle.context.event;

import java.util.EventObject;

public abstract class Event extends EventObject {

	private static final long serialVersionUID = 6311495014194589511L;
	
	/** System time when the event happened */
	private final long timestamp;

	/**
	 * Create a new ApplicationEvent.
	 * @param source the component that published the event (never <code>null</code>)
	 */
	public Event(Object source) {
		super(source);
		this.timestamp = System.currentTimeMillis();
	}


	/**
	 * Return the system time in milliseconds when the event happened.
	 */
	public final long getTimestamp() {
		return this.timestamp;
	}

}

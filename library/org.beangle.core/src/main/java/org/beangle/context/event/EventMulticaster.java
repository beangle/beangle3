package org.beangle.context.event;


public interface EventMulticaster {
	/**
	 * Add a listener to be notified of all events.
	 * 
	 * @param listener the listener to add
	 */
	public void addListener(EventListener<?> listener);

	/**
	 * Remove a listener from the notification list.
	 * 
	 * @param listener the listener to remove
	 */
	public void removeListener(EventListener<?> listener);

	/**
	 * Remove all listeners registered with this multicaster.
	 * <p>
	 * After a remove call, the multicaster will perform no action on event notification until new
	 * listeners are being registered.
	 */
	public void removeAllListeners();

	/**
	 * @param e
	 */
	public void multicast(Event e);
}

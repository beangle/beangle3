package org.beangle.context.event;


public interface EventListener<E extends Event> extends java.util.EventListener {
	/**
	 * Handle an application event.
	 * 
	 * @param event the event to respond to
	 */
	public void onEvent(E event);

	/**
	 * Determine whether this listener actually supports the given event type.
	 */
	public boolean supportsEventType(Class<? extends Event> eventType);

	/**
	 * Determine whether this listener actually supports the given source type.
	 */
	boolean supportsSourceType(Class<?> sourceType);
}

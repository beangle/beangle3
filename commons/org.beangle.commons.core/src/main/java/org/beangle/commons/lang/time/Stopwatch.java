/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang.time;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.beangle.commons.lang.Assert;

/**
 * @author chaostone
 * @since 3.0.0
 */
public final class Stopwatch {
  private final Ticker ticker;
  private boolean isRunning;
  private long elapsedNanos;
  private long startTick;

  /**
   * Creates (but does not start) a new stopwatch using {@link System#nanoTime} as its time source.
   */
  public Stopwatch() {
    this(Ticker.systemTicker());
  }

  /**
   * Creates (but does not start) a new stopwatch, using the specified time
   * source.
   */
  public Stopwatch(Ticker ticker) {
    this.ticker = ticker;
  }

  /**
   * Returns {@code true} if {@link #start()} has been called on this stopwatch,
   * and {@link #stop()} has not been called since the last call to {@code start()}.
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**
   * Starts the stopwatch.
   * 
   * @return this {@code Stopwatch} instance
   * @throws IllegalStateException if the stopwatch is already running.
   */
  public Stopwatch start() {
    Assert.isTrue(!isRunning);
    isRunning = true;
    startTick = ticker.read();
    return this;
  }

  /**
   * Stops the stopwatch. Future reads will return the fixed duration that had
   * elapsed up to this point.
   * 
   * @return this {@code Stopwatch} instance
   * @throws IllegalStateException if the stopwatch is already stopped.
   */
  public Stopwatch stop() {
    long tick = ticker.read();
    Assert.isTrue(isRunning);
    isRunning = false;
    elapsedNanos += tick - startTick;
    return this;
  }

  /**
   * Sets the elapsed time for this stopwatch to zero,
   * and places it in a stopped state.
   * 
   * @return this {@code Stopwatch} instance
   */
  public Stopwatch reset() {
    elapsedNanos = 0;
    isRunning = false;
    return this;
  }

  private long elapsedNanos() {
    return isRunning ? ticker.read() - startTick + elapsedNanos : elapsedNanos;
  }

  /**
   * Returns the current elapsed time shown on this stopwatch, expressed
   * in the desired time unit, with any fraction rounded down.
   * <p>
   * Note that the overhead of measurement can be more than a microsecond, so it is generally not
   * useful to specify {@link TimeUnit#NANOSECONDS} precision here.
   */
  public long elapsedTime(TimeUnit desiredUnit) {
    return desiredUnit.convert(elapsedNanos(), NANOSECONDS);
  }

  /**
   * Returns the current elapsed time shown on this stopwatch, expressed
   * in milliseconds, with any fraction rounded down. This is identical to
   * {@code elapsedTime(TimeUnit.MILLISECONDS}.
   */
  public long elapsedMillis() {
    return elapsedTime(MILLISECONDS);
  }

  /**
   * Returns a string representation of the current elapsed time; equivalent to {@code toString(4)}
   * (four significant figures).
   */
  @Override
  public String toString() {
    return toString(4);
  }

  /**
   * Returns a string representation of the current elapsed time, choosing an
   * appropriate unit and using the specified number of significant figures.
   * For example, at the instant when {@code elapsedTime(NANOSECONDS)} would
   * return {1234567}, {@code toString(4)} returns {@code "1.235 ms"}.
   */
  public String toString(int significantDigits) {
    long nanos = elapsedNanos();

    TimeUnit unit = chooseUnit(nanos);
    double value = (double) nanos / NANOSECONDS.convert(1, unit);

    // Too bad this functionality is not exposed as a regular method call
    return String.format("%." + significantDigits + "g %s", value, abbreviate(unit));
  }

  private static TimeUnit chooseUnit(long nanos) {
    if (SECONDS.convert(nanos, NANOSECONDS) > 0) { return SECONDS; }
    if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) { return MILLISECONDS; }
    if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) { return MICROSECONDS; }
    return NANOSECONDS;
  }

  private static String abbreviate(TimeUnit unit) {
    switch (unit) {
    case NANOSECONDS:
      return "ns";
    case MICROSECONDS:
      return "\u03bcs"; // μs
    case MILLISECONDS:
      return "ms";
    case SECONDS:
      return "s";
    default:
      throw new AssertionError();
    }
  }
}

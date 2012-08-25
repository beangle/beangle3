/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang.time;

/**
 * Time source generator
 * 
 * @author chaostone
 * @since 3.0.0
 */
public abstract class Ticker {

  protected Ticker() {
  }

  /**
   * Returns the number of nanoseconds elapsed
   */
  public abstract long read();

  /**
   * A ticker that reads the current time using {@link System#nanoTime}.
   */
  public static Ticker systemTicker() {
    return SYSTEM_TICKER;
  }

  private static final Ticker SYSTEM_TICKER = new Ticker() {
    @Override
    public long read() {
      return System.nanoTime();
    }
  };
}

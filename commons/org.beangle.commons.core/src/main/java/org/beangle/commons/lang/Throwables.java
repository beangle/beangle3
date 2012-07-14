/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Static utility methods pertaining to instances of {@link Throwable}.
 * 
 * @author chaostone
 * @since 3.0
 */
public final class Throwables {
  private Throwables() {
  }

  /**
   * Propagates {@code throwable} exactly as-is, if and only if it is an
   * instance of {@code declaredType}. Example usage:
   * 
   * <pre>
   * try {
   *   someMethodThatCouldThrowAnything();
   * } catch (IKnowWhatToDoWithThisException e) {
   *   handle(e);
   * } catch (Throwable t) {
   *   Throwables.propagateIfInstanceOf(t, IOException.class);
   *   Throwables.propagateIfInstanceOf(t, SQLException.class);
   *   throw Throwables.propagate(t);
   * }
   * </pre>
   */
  public static <X extends Throwable> void propagateIfInstanceOf(Throwable throwable, Class<X> declaredType)
      throws X {
    // Check for null is needed to avoid frequent JNI calls to isInstance().
    if (throwable != null && declaredType.isInstance(throwable)) { throw declaredType.cast(throwable); }
  }


  /**
   * Propagates {@code throwable} as-is if it is an instance of {@link RuntimeException} or
   * {@link Error}, or else as a last resort, wraps
   * it in a {@code RuntimeException} then propagates.
   * <p>
   * This method always throws an exception. The {@code RuntimeException} return type is only for
   * client code to make Java type system happy in case a return value is required by the enclosing
   * method. Example usage:
   * 
   * <pre>
   * T doSomething() {
   *   try {
   *     return someMethodThatCouldThrowAnything();
   *   } catch (IKnowWhatToDoWithThisException e) {
   *     return handle(e);
   *   } catch (Throwable t) {
   *     throw Throwables.propagate(t);
   *   }
   * }
   * </pre>
   * 
   * @param throwable the Throwable to propagate
   * @return nothing will ever be returned; this return type is only for your
   *         convenience, as illustrated in the example above
   */
  public static RuntimeException propagate(Throwable throwable) {
    propagateIfInstanceOf(throwable, Error.class);
    propagateIfInstanceOf(throwable, RuntimeException.class);
    throw new RuntimeException(throwable);
  }

  /**
   * Returns a string containing the result of {@link Throwable#toString() toString()}, followed by
   * the full, recursive
   * stack trace of {@code throwable}. Note that you probably should not be
   * parsing the resulting string; if you need programmatic access to the stack
   * frames, you can call {@link Throwable#getStackTrace()}.
   */
  public static String getStackTraceAsString(Throwable throwable) {
    StringWriter stringWriter = new StringWriter();
    throwable.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }
}

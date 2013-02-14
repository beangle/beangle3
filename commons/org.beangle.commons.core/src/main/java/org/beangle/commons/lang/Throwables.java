/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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
  public static String getStackTrace(Throwable throwable) {
    StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
}

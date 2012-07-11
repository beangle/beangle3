/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;


public final class Objects {

  /**
   * <p>
   * Compares two objects for equality, where either one or both objects may be {@code null}.
   * </p>
   * 
   * <pre>
   * equals(null, null)                  = true
   * equals(null, "")                    = false
   * equals("", null)                    = false
   * equals("", "")                      = true
   * equals(Boolean.TRUE, null)          = false
   * equals(Boolean.TRUE, "true")        = false
   * equals(Boolean.TRUE, Boolean.TRUE)  = true
   * equals(Boolean.TRUE, Boolean.FALSE) = false
   * </pre>
   * 
   * @param a the first object, may be {@code null}
   * @param b the second object, may be {@code null}
   * @return {@code true} if the values of both objects are the same
   * @since 3.0
   */
  public static boolean equals(Object a, Object b) {
    return (a == b) || (a != null && a.equals(b));
  }

  /**
   * <p>
   * Gets the {@code toString} of an {@code Object} returning an empty string ("") if {@code null}
   * input.
   * </p>
   * 
   * <pre>
   * toString(null)         = ""
   * toString("")           = ""
   * toString("bat")        = "bat"
   * toString(Boolean.TRUE) = "true"
   * </pre>
   * 
   * @see String#valueOf(Object)
   * @param obj the Object to {@code toString}, may be null
   * @return the passed in Object's toString, or nullStr if {@code null} input
   * @since 3.0
   */
  public static String toString(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  // -----------------------------------------------------------------------
  /**
   * <p>
   * Returns a default value if the object passed is {@code null}.
   * </p>
   * 
   * <pre>
   * defaultIfNull(null, null)      = null
   * defaultIfNull(null, "")        = ""
   * defaultIfNull(null, "zz")      = "zz"
   * defaultIfNull("abc", *)        = "abc"
   * defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
   * </pre>
   * 
   * @param <T> the type of the object
   * @param object the {@code Object} to test, may be {@code null}
   * @param defaultValue the default value to return, may be {@code null}
   * @return {@code object} if it is not {@code null}, defaultValue otherwise
   * @since 3.0
   */
  public static <T> T defaultIfNull(T object, T defaultValue) {
    return object != null ? object : defaultValue;
  }

  /**
   * Return a hex String form of an object's identity hash code.
   * 
   * @param obj the object
   * @return the object's identity code in hex notation
   */
  public static String getIdentityHexString(Object obj) {
    return Integer.toHexString(System.identityHashCode(obj));
  }
}

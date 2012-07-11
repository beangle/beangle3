/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

/**
 * <p>
 * Thrown when it is impossible or undesirable to consume or throw a checked exception.
 * </p>
 * This exception supplements the standard exception classes by providing a more
 * semantically rich description of the problem.</p>
 * <p>
 * <code>UnhandledException</code> represents the case where a method has to deal with a checked
 * exception but does not wish to. Instead, the checked exception is rethrown in this unchecked
 * wrapper.
 * </p>
 * 
 * <pre>
 * public void foo() {
 *   try {
 *     // do something that throws IOException
 *   } catch (IOException ex) {
 *     // don't want to or can't throw IOException from foo()
 *     throw new UnhandledException(ex);
 *   }
 * }
 * </pre>
 * 
 * @author chaostone
 * @since 3.0
 */
public class UnhandledException extends RuntimeException {
  /**
   * Required for serialization support.
   * 
   * @see java.io.Serializable
   */
  private static final long serialVersionUID = 1L;

  /**
   * Holds the reference to the exception or error that caused
   * this exception to be thrown.
   */
  private Throwable cause = null;

  /**
   * Constructs a new <code>UnhandledException</code> without specified
   * detail message.
   */
  public UnhandledException() {
    super();
  }

  /**
   * Constructs a new <code>UnhandledException</code> with specified
   * detail message.
   * 
   * @param msg the error message
   */
  public UnhandledException(String msg) {
    super(msg);
  }

  /**
   * Constructs a new <code>UnhandledException</code> with specified
   * nested <code>Throwable</code>.
   * 
   * @param cause the exception or error that caused this exception to be
   *          thrown
   */
  public UnhandledException(Throwable cause) {
    super();
    this.cause = cause;
  }

  /**
   * Constructs a new <code>UnhandledException</code> with specified
   * detail message and nested <code>Throwable</code>.
   * 
   * @param msg the error message
   * @param cause the exception or error that caused this exception to be
   *          thrown
   */
  public UnhandledException(String msg, Throwable cause) {
    super(msg);
    this.cause = cause;
  }

  /**
   * {@inheritDoc}
   */
  public Throwable getCause() {
    return cause;
  }

  /**
   * Returns the detail message string of this throwable. If it was
   * created with a null message, returns the following:
   * (cause==null ? null : cause.toString()).
   * 
   * @return String message string of the throwable
   */
  public String getMessage() {
    if (super.getMessage() != null) {
      return super.getMessage();
    } else if (cause != null) {
      return cause.toString();
    } else {
      return null;
    }
  }
}

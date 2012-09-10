/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer;

/**
 * IllegalImportFormat
 * 
 * @author chaostone
 * @since 3.0.0
 */
@SuppressWarnings("serial")
public class IllegalImportFormatException extends RuntimeException {

  public IllegalImportFormatException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public IllegalImportFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalImportFormatException(String message) {
    super(message);
  }

  public IllegalImportFormatException(Throwable cause) {
    super(cause);
  }

}

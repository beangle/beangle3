/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.validation;

/**
 * @author chaostone
 * @version $Id: TicketValidationException.java Dec 27, 2011 6:03:24 PM chaostone $
 */
public class TicketValidationException extends Exception {

  private static final long serialVersionUID = -6975497807681884113L;

  public TicketValidationException(final String string) {
    super(string);
  }

  public TicketValidationException(final String string, final Throwable throwable) {
    super(string, throwable);
  }

  public TicketValidationException(final Throwable throwable) {
    super(throwable);
  }

}

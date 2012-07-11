/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.validation;

/**
 * @author chaostone
 * @version $Id: TicketValidator.java Dec 27, 2011 6:02:18 PM chaostone $
 */
public interface TicketValidator {

  Assertion validate(String ticket, String service) throws TicketValidationException;
}

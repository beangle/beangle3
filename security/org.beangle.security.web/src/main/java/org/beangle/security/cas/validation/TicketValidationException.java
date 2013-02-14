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

/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.script;

/**
 * <p>
 * EvaluationException class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: EvaluationException.java Mar 5, 2012 12:13:41 AM chaostone $
 */
public class EvaluationException extends RuntimeException {

  private static final long serialVersionUID = 7366966661039007890L;

  /**
   * <p>
   * Constructor for EvaluationException.
   * </p>
   * 
   * @param cause a {@link java.lang.Throwable} object.
   */
  public EvaluationException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs the exception using a message and cause.
   * 
   * @param message the message to use
   * @param cause the underlying cause
   */
  public EvaluationException(String message, Throwable cause) {
    super(message, cause);
  }
}

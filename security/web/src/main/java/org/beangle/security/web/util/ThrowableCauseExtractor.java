/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web.util;

/**
 * Interface for handlers extracting the cause out of a specific {@link Throwable} type.
 *
 * @author Andreas Senft
 * @since 2.0
 * @version $Id: ThrowableCauseExtractor.java 2559 2008-01-30 16:15:02Z $
 * @see ThrowableAnalyzer
 */
public interface ThrowableCauseExtractor {

  /**
   * Extracts the cause from the provided <code>Throwable</code>.
   *
   * @param throwable
   *          the <code>Throwable</code>
   * @return the extracted cause (maybe <code>null</code>)
   * @throws IllegalArgumentException
   *           if <code>throwable</code> is <code>null</code> or otherwise
   *           considered invalid for the implementation
   */
  Throwable extractCause(Throwable throwable);
}

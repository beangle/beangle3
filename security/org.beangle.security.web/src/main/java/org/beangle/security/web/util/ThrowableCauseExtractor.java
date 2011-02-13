/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
	 *            the <code>Throwable</code>
	 * @return the extracted cause (maybe <code>null</code>)
	 * @throws IllegalArgumentException
	 *             if <code>throwable</code> is <code>null</code> or otherwise
	 *             considered invalid for the implementation
	 */
	Throwable extractCause(Throwable throwable);
}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core;

import java.io.Serializable;

/**
 * Represents an authority granted to an {@link Authentication} object.
 */
public interface GrantedAuthority extends Serializable, Comparable<GrantedAuthority> {
	/**
	 * If the <code>GrantedAuthority</code> can be represented as a <code>String</code> and that
	 * <code>String</code> is sufficient in
	 * precision to be relied upon for an access control decision by an
	 * {@link AccessDecisionManager} (or delegate), this method should return
	 * such a <code>String</code>.
	 * <p>
	 * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision as a
	 * <code>String</code>, <code>null</code> should be returned. Returning <code>null</code> will
	 * require an <code>AccessDecisionManager</code> (or delegate) to specifically support the
	 * <code>GrantedAuthority</code> implementation, so returning <code>null</code> should be
	 * avoided unless actually required.
	 * </p>
	 * 
	 * @return a representation of the granted authority (or <code>null</code> if the granted
	 *         authority cannot be expressed as a <code>String</code> with sufficient precision).
	 */
	String getAuthority();
}

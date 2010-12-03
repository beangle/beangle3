/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

public interface AuthenticationDetailsSource<C, T> {

	T buildDetails(C context);
}

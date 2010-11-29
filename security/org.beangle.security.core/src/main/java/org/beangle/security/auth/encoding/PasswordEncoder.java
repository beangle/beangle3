/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth.encoding;

public interface PasswordEncoder {

	public boolean isPasswordValid(String encPass, String rawPass);

}

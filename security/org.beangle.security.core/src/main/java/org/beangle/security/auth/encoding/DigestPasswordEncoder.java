/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth.encoding;

import org.beangle.security.codec.EncryptUtil;

public class DigestPasswordEncoder implements PasswordEncoder {

	private String algorithm = "MD5";

	public boolean isPasswordValid(String encPass, String rawPass) {
		return encPass.equals(EncryptUtil.encode(rawPass, algorithm));
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

}

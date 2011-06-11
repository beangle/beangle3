package org.beangle.emsapp.demo;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.web.auth.preauth.UsernameSource;

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */

public class GivenUsernameSource implements UsernameSource {

	public String obtainUsername(HttpServletRequest request) {
		return "admin";
	}

}

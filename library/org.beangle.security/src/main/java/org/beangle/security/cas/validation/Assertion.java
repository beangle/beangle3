/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.validation;

import java.util.Date;
import java.util.Map;

/**
 * @author chaostone
 * @version $Id: Assertion.java Dec 27, 2011 5:55:02 PM chaostone $
 */
public interface Assertion {

	public String getPrincipal();
	
	public String getTicket();

	public Date getValidAt();

	public Map<String, Object> getAttributes();

}

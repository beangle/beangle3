/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class RequestUtilsTest {

	public void testGetServletPath() {
		MockHttpServletRequest request = new MockHttpServletRequest(null, "/");
		request.setContextPath("/");
		Assert.assertEquals(RequestUtils.getServletPath(request), "");
		request.setContextPath("/");
		request.setRequestURI("/demo");
		Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");

		request.setContextPath("");
		request.setRequestURI("/demo");
		Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");
	}
}

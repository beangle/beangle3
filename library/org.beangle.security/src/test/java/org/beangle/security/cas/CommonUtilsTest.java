/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas;

import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * @version $Id: CommonUtilsTest.java Nov 8, 2010 8:59:43 AM chaostone $
 */
@Test
public class CommonUtilsTest {

	public void testConstuctServiceUrl() {
		CasConfig config = new CasConfig();
		config.setCasServer("http://www.mycompany.com/cas");
		config.setLocalServer("localhost:8080/demo");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContextPath("/demo");
		request.setServletPath("/home.action");
		request.setRequestURI("/demo/home.action");
		System.out.println(request.getRequestURI());
		HttpServletResponse response = new MockHttpServletResponse();
		final String urlEncodedService = CommonUtils.constructServiceUrl(request, response, null,
				config.getLocalServer(), "ticket", config.isEncode());
		System.out.println(urlEncodedService);

		final String urlEncodedService2 = CommonUtils.constructServiceUrl(request, response, null,
				"localhost:8080", "ticket", config.isEncode());
		
		System.out.println(urlEncodedService2);
	}
}

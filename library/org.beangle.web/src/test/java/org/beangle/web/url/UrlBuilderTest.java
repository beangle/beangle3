/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.url;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author chaostone
 * @version $Id: UrlBuilderTest.java Nov 13, 2010 9:39:00 AM chaostone $
 */
@Test
public class UrlBuilderTest {

	public void buildFullUrl() {
		UrlBuilder builder = new UrlBuilder("/");
		builder.scheme("http").serverName("localhost").port(80);
		builder.requestURI("/demo/security/user");
		builder.queryString("name=1&fullname=join");
		assertEquals(builder.buildUrl(), "http://localhost/demo/security/user?name=1&fullname=join");
		builder.requestURI(null).port(8080).servletPath("/security");
		assertEquals(builder.buildUrl(), "http://localhost:8080/security?name=1&fullname=join");
	}

	public void build() {
		UrlBuilder builder = new UrlBuilder("/");
		builder.servletPath("/security/user");
		builder.queryString("name=1&fullname=join");
		assertEquals(builder.buildRequestUrl(), "/security/user?name=1&fullname=join");
		builder.requestURI("/demo/security/user");
		assertEquals(builder.buildRequestUrl(), "/security/user?name=1&fullname=join");
	}
}

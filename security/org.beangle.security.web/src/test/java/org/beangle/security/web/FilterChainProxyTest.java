/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.servlet.Filter;

import org.beangle.commons.web.filter.MockFilter;
import org.beangle.security.web.auth.UsernamePasswordAuthenticationFilter;
import org.beangle.security.web.context.HttpSessionContextFilter;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration("classpath:org/beangle/security/web/filters-test.xml")
public class FilterChainProxyTest extends AbstractTestNGSpringContextTests {

  public void normalOperation() throws Exception {
    FilterChainProxy filterChainProxy = (FilterChainProxy) applicationContext.getBean("filterChain",
        FilterChainProxy.class);
    checkPathAndFilterOrder(filterChainProxy);
    doNormalOperation(filterChainProxy);
  }

  public void noMatchFilters() throws Exception {
    FilterChainProxy filterChainProxy = (FilterChainProxy) applicationContext.getBean(
        "newFilterChainProxyNoDefaultPath", FilterChainProxy.class);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath("/nomatch");
    assertEquals(null, filterChainProxy.getFilters(request));
  }

  public void urlStrippingPropertyIsRespected() throws Exception {
    FilterChainProxy filterChainProxy = (FilterChainProxy) applicationContext.getBean(
        "newFilterChainProxyNoDefaultPath", FilterChainProxy.class);
    String url = "/blah.bar";
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(url);
    request.setQueryString("x=something");
    assertNotNull(filterChainProxy.getFilters(request));
    assertEquals(2, filterChainProxy.getFilters(request).size());
  }

  private void checkPathAndFilterOrder(FilterChainProxy filterChainProxy) throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath("/foo/blah");
    List<Filter> filters = filterChainProxy.getFilters(request);
    assertEquals(1, filters.size());
    assertTrue(filters.get(0) instanceof MockFilter);

    request.setServletPath("/some/other/path/blah");
    filters = filterChainProxy.getFilters(request);
    assertNotNull(filters);
    assertEquals(3, filters.size());
    assertTrue(filters.get(0) instanceof HttpSessionContextFilter);
    assertTrue(filters.get(1) instanceof MockFilter);
    assertTrue(filters.get(2) instanceof MockFilter);

    request.setServletPath("/do/not/filter");
    filters = filterChainProxy.getFilters(request);
    assertEquals(0, filters.size());

    request.setServletPath("/another/nonspecificmatch");
    filters = filterChainProxy.getFilters(request);
    assertEquals(3, filters.size());
    assertTrue(filters.get(0) instanceof HttpSessionContextFilter);
    assertTrue(filters.get(1) instanceof UsernamePasswordAuthenticationFilter);
    assertTrue(filters.get(2) instanceof MockFilter);
  }

  private void doNormalOperation(FilterChainProxy filterChainProxy) throws Exception {
    MockFilter filter = (MockFilter) applicationContext.getBean("mockFilter", MockFilter.class);
    assertFalse(filter.isInitialized());
    assertFalse(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    filter.init(new MockFilterConfig());
    assertTrue(filter.isInitialized());
    assertFalse(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath("/foo/secure/super/somefile.html");
    request.setContextPath("/");

    MockHttpServletResponse response = new MockHttpServletResponse();

    filterChainProxy.doFilter(request, response, new MockFilterChain());
    assertTrue(filter.isInitialized());
    assertTrue(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    request.setServletPath("/a/path/which/doesnt/match/any/filter.html");
    filterChainProxy.doFilter(request, response, new MockFilterChain());

    filter.destroy();
    assertTrue(filter.isInitialized());
    assertTrue(filter.isDoFiltered());
    assertTrue(filter.isDestroyed());
  }
}

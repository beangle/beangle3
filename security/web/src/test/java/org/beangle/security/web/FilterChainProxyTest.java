/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.web.mock.MockFilter;
import org.beangle.security.web.auth.UsernamePasswordAuthFilter;
import org.beangle.security.web.context.HttpSessionContextFilter;
import org.testng.annotations.Test;

@Test
public class FilterChainProxyTest {

  Filter mockFilter = new MockFilter();
  Filter mockFilter2 = new MockFilter();
  HttpSessionContextFilter sif = new HttpSessionContextFilter();
  UsernamePasswordAuthFilter apf = new UsernamePasswordAuthFilter();

  public void normalOperation() throws Exception {
    Map<String, List<Filter>> chainMap = CollectUtils.newLinkedHashMap(4);
    chainMap.put("/foo/**", CollectUtils.newArrayList(mockFilter));
    chainMap.put("/some/other/path/**", CollectUtils.newArrayList(sif, mockFilter, mockFilter2));
    chainMap.put("/do/not/filter", new ArrayList<Filter>());
    chainMap.put("/**", CollectUtils.newArrayList(sif, apf, mockFilter));

    FilterChainProxy proxy = new FilterChainProxy(chainMap);
    checkPathAndFilterOrder(proxy);
    doNormalOperation(proxy);
  }

  public void noMatchFilters() throws Exception {
    Map<String, List<Filter>> chainMap = CollectUtils.newHashMap();
    chainMap.put("/foo/**", CollectUtils.newArrayList(mockFilter));
    chainMap.put("/*.bar", CollectUtils.newArrayList(mockFilter, mockFilter2));
    FilterChainProxy proxy = new FilterChainProxy(chainMap);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/nomatch");
    assertEquals(0,proxy.getFilters(request).size());
  }

  public void urlStrippingPropertyIsRespected() throws Exception {
    Map<String, List<Filter>> chainMap = CollectUtils.newHashMap();
    chainMap.put("/foo/**", CollectUtils.newArrayList(mockFilter));
    chainMap.put("/*.bar", CollectUtils.newArrayList(mockFilter, mockFilter2));
    FilterChainProxy proxy = new FilterChainProxy(chainMap);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/blah.bar");
    when(request.getQueryString()).thenReturn("x=something");
    assertNotNull(proxy.getFilters(request));
    assertEquals(2, proxy.getFilters(request).size());
  }

  private void checkPathAndFilterOrder(FilterChainProxy chain) throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/foo/blah");
    List<Filter> filters = chain.getFilters(request);
    assertEquals(1, filters.size());
    assertTrue(filters.get(0) instanceof MockFilter);

    when(request.getServletPath()).thenReturn("/some/other/path/blah");
    filters = chain.getFilters(request);
    assertNotNull(filters);
    assertEquals(3, filters.size());
    assertTrue(filters.get(0) instanceof HttpSessionContextFilter);
    assertTrue(filters.get(1) instanceof MockFilter);
    assertTrue(filters.get(2) instanceof MockFilter);

    when(request.getServletPath()).thenReturn("/do/not/filter");
    filters = chain.getFilters(request);
    assertEquals(0, filters.size());

    when(request.getServletPath()).thenReturn("/another/nonspecificmatch");
    filters = chain.getFilters(request);
    assertEquals(3, filters.size());
    assertTrue(filters.get(0) instanceof HttpSessionContextFilter);
    assertTrue(filters.get(1) instanceof UsernamePasswordAuthFilter);
    assertTrue(filters.get(2) instanceof MockFilter);
  }

  private void doNormalOperation(FilterChainProxy chain) throws Exception {
    MockFilter filter = (MockFilter) mockFilter;
    assertFalse(filter.isInitialized());
    assertFalse(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    filter.init(mock(FilterConfig.class));
    assertTrue(filter.isInitialized());
    assertFalse(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getServletPath()).thenReturn("/foo/secure/super/somefile.html");
    when(request.getContextPath()).thenReturn("/");

    HttpServletResponse response = mock(HttpServletResponse.class);

    chain.doFilter(request, response, mock(FilterChain.class));
    assertTrue(filter.isInitialized());
    assertTrue(filter.isDoFiltered());
    assertFalse(filter.isDestroyed());

    when(request.getContextPath()).thenReturn("/a");
    when(request.getRequestURI()).thenReturn("/a/path/which/doesnt/match/any/filter.html");
    when(request.getServletPath()).thenReturn("/path/which/doesnt/match/any/filter.html");
    chain.doFilter(request, response, mock(FilterChain.class));

    filter.destroy();
    assertTrue(filter.isInitialized());
    assertTrue(filter.isDoFiltered());
    assertTrue(filter.isDestroyed());
  }
}

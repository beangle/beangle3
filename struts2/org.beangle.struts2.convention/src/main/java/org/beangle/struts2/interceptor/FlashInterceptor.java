/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.interceptor;

import org.beangle.struts2.convention.Flash;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * ROR's flash
 * 
 * @author chaostone
 */
public class FlashInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 8451445989084058881L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    String result = invocation.invoke();
    try {
      Flash flash = (Flash) invocation.getInvocationContext().getSession().get("flash");
      if (null != flash) flash.nextToNow();
    } catch (IllegalStateException e) {
    }
    return result;
  }

}

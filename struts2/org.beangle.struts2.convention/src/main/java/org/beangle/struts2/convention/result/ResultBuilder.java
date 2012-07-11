/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ActionConfig;

/**
 * 为构建自定义的结果，抽象出的一个接口
 * 
 * @author chaostone
 */
public interface ResultBuilder {

  public Result build(String resultCode, ActionConfig actionConfig, ActionContext context);
}

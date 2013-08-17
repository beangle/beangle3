/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.convention;

import org.beangle.struts2.convention.result.ResultBuilder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 实现action到result之间的路由和处理<br>
 * 默认按照方法进行路由
 * 
 * @author chaostone
 */
public class ConventionRouteHandler implements UnknownHandler {

  @Inject
  protected ResultBuilder resultBuilder;

  public ActionConfig handleUnknownAction(String namespace, String actionName) throws XWorkException {
    return null;
  }

  public Object handleUnknownActionMethod(Object arg0, String arg1) throws NoSuchMethodException {
    return null;
  }

  public Result handleUnknownResult(ActionContext actionContext, String actionName,
      ActionConfig actionConfig, String resultCode) throws XWorkException {
    return resultBuilder.build(resultCode, actionConfig, actionContext);
  }
}

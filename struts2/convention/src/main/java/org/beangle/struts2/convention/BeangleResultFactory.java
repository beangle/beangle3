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
package org.beangle.struts2.convention;

import java.util.Map;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.factory.ResultFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.reflection.ReflectionException;
import com.opensymphony.xwork2.util.reflection.ReflectionExceptionHandler;
import com.opensymphony.xwork2.util.reflection.ReflectionProvider;

/**
 * Build Result
 * 1 Specialize for freemarker result creation.
 * 
 * @author chaostone
 */
public class BeangleResultFactory implements ResultFactory {

  protected FreemarkerManager freemarkerManager;
  protected ObjectFactory objectFactory;
  protected ReflectionProvider reflectionProvider;

  /**
   * 为freemaker做优化
   */
  public Result buildResult(ResultConfig resultConfig, Map<String, Object> extraContext) throws Exception {
    String resultClassName = resultConfig.getClassName();
    Result result = null;
    if (resultClassName != null) {
      if (resultClassName.equals("org.apache.struts2.views.freemarker.FreemarkerResult")) {
        result = new FreemarkerResult(resultConfig.getParams().get("location"));
        ((FreemarkerResult) result).setFreemarkerManager(freemarkerManager);
      } else {
        result = (Result) objectFactory.buildBean(resultClassName, extraContext);
        Map<String, String> params = resultConfig.getParams();
        if (params != null) {
          for (Map.Entry<String, String> paramEntry : params.entrySet()) {
            try {
              reflectionProvider.setProperty(paramEntry.getKey(), paramEntry.getValue(), result,
                  extraContext, true);
            } catch (ReflectionException ex) {
              if (result instanceof ReflectionExceptionHandler) ((ReflectionExceptionHandler) result)
                  .handle(ex);
            }
          }
        }
      }
    }
    return result;
  }

  @Inject
  public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
    this.freemarkerManager = freemarkerManager;
  }

  @Inject
  public void setObjectFactory(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;
  }

  @Inject
  public void setReflectionProvider(ReflectionProvider reflectionProvider) {
    this.reflectionProvider = reflectionProvider;
  }

}

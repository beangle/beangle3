/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.config.ConfigurationManager;

/**
 * 映射URI到struts对应的Action,兼容原有的method形式<br>
 * <ul>
 * <li>默认方法更改为index<br>
 * <li>可以接受method=的形式重新指定方法<br>
 * <li>默认使用action!method的方式进行uri生成
 * </ul>
 * 
 * @author chaostone
 * @since 2.0
 */
public class ConventionActionMapper extends DefaultActionMapper implements ActionMapper {

  private static final String MethodParam = "method";

  private static final String DefaultMethod = "index";

  /**
   * reserved method parameter
   */
  public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
    ActionMapping mapping = super.getMapping(request, configManager);
    if (null != mapping) {
      String method = request.getParameter(MethodParam);
      if (Strings.isNotEmpty(method)) mapping.setMethod(method);
      if (null == mapping.getMethod()) mapping.setMethod(DefaultMethod);
    }
    return mapping;
  }

}

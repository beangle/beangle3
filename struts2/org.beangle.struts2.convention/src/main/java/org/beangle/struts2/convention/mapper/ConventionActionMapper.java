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
import org.beangle.commons.web.util.RequestUtils;

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

  protected void parseNameAndNamespace(String uri, ActionMapping mapping) {
    String namespace, name;
    int lastSlash = uri.lastIndexOf("/");
    if (lastSlash == -1) {
      namespace = "";
      name = uri;
    } else if (lastSlash == 0) {
      namespace = "/";
      name = uri.substring(lastSlash + 1);
    } else {
      // Simply select the namespace as everything before the last slash
      namespace = uri.substring(0, lastSlash);
      name = uri.substring(lastSlash + 1);
    }
    // process ! . ;
    int i = 0;
    int bangIdx = -1;
    int lastIdx = name.length();
    char[] chars = new char[name.length()];
    name.getChars(0, name.length(), chars, 0);
    while (i < chars.length) {
      char c = chars[i];
      if ('!' == c) bangIdx = i;
      else if (';' == c) {
        lastIdx = i;
        break;
      }else if ('.' == c) {
        lastIdx = i;
        break;
      }
      i++;
    }

    mapping.setNamespace(namespace);
    if (-1 == bangIdx) {
      mapping.setName(name.substring(0, lastIdx));
      mapping.setMethod(DefaultMethod);
    } else {
      mapping.setName(name.substring(0, bangIdx));
      mapping.setMethod(name.substring(bangIdx + 1, lastIdx));
    }

  }

  /**
   * reserved method parameter
   */
  public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
    ActionMapping mapping = new ActionMapping();
    parseNameAndNamespace(RequestUtils.getServletPath(request), mapping);
    
    String method = request.getParameter(MethodParam);
    if (Strings.isNotEmpty(method)) mapping.setMethod(method);
    return mapping;
  }

}

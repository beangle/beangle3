/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.struts2.view.freemarker.BeangleModels;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Beangle tag Library
 * 
 * @author chaostone
 * @since 2.0
 */
public class BeangleTagLibrary extends AbstractTagLibrary {

  public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    return new BeangleModels(stack, req, res);
  }

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class Validity extends ClosingUIBean {

  public Validity(ValueStack stack) {
    super(stack);
  }

  public boolean doEnd(Writer writer, String body) {
    Form myform = findAncestor(Form.class);
    if (null != myform) myform.addCheck(body);
    return false;
  }

}

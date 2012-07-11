/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Field extends ClosingUIBean {

  private String label;

  private String required;

  public Field(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    if (null != label) {
      label = getText(label);
    }
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import org.beangle.struts2.view.component.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @since 3.0.1
 */
public class DefaultDirectives {

  public static class Toolbar extends ComponentDirective {

    public Toolbar(ValueStack stack) {
      super(stack);
    }

    @Override
    protected Component getComponent() {
      return new org.beangle.struts2.view.component.Toolbar(stack);
    }

  }

}

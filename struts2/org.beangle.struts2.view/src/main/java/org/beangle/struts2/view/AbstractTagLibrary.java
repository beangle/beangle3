/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

import java.util.List;

import org.apache.struts2.views.TagLibrary;

/**
 * @author chaostone
 * @since 2.4
 */
public abstract class AbstractTagLibrary implements TagLibrary {

  @SuppressWarnings("rawtypes")
  public List<Class> getVelocityDirectiveClasses() {
    return null;
  }
}

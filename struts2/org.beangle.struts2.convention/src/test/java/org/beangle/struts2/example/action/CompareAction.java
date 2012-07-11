/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.example.action;

import org.beangle.struts2.action.EntityActionSupport;

public class CompareAction extends EntityActionSupport {

  public String index() {
    return SUCCESS;
  }

  @Override
  protected String getEntityName() {
    return null;
  }

}

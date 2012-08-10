/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 属性
 * 
 * @author chaostone
 * @version $Id: Property.java Oct 18, 2011 8:57:24 AM chaostone $
 */
public interface Property extends LongIdEntity {

  public static final String AllValue = "*";

  public PropertyMeta getMeta();

  public String getValue();

  public void setValue(String value);
}

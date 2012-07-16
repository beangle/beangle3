/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.event;

import java.util.List;

import org.beangle.commons.dao.Entity;

/**
 * 实体创建事件
 * 
 * @author chaostone
 * @version $Id: EntityCreationEvent.java Mar 3, 2012 9:05:40 PM chaostone $
 */
public class EntityCreationEvent<T extends Entity<?>> extends AbstractEntityEvent<T> {

  private static final long serialVersionUID = -3370162531310618366L;

  public EntityCreationEvent(Class<T> clazz, List<T> source) {
    super(clazz, source);
  }
}

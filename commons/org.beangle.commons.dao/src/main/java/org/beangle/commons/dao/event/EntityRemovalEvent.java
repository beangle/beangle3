/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.event;

import java.util.List;

import org.beangle.commons.dao.Entity;

/**
 * 实体删除事件
 * 
 * @author chaostone
 * @version $Id: EntityRemoveEvent.java Mar 3, 2012 9:01:44 PM chaostone $
 */
public class EntityRemovalEvent<T extends Entity<?>> extends AbstractEntityEvent<T> {

  private static final long serialVersionUID = 8762083590144399436L;

  public EntityRemovalEvent(Class<T> clazz, List<T> source) {
    super(clazz, source);
  }

}

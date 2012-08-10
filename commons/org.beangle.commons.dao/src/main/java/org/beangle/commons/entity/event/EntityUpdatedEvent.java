/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.event;

import java.util.List;

import org.beangle.commons.entity.Entity;

/**
 * 实体更新事件
 * 
 * @author chaostone
 * @version $Id: EntityUpdatedEvent.java Mar 3, 2012 9:05:03 PM chaostone $
 */
public class EntityUpdatedEvent<T extends Entity<?>> extends AbstractEntityEvent<T> {

  private static final long serialVersionUID = 9143547450045431391L;

  public EntityUpdatedEvent(Class<T> clazz, List<T> source) {
    super(clazz, source);
  }
}

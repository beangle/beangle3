/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao;

import java.io.Serializable;

/**
 * 实体类接口
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Entity<ID extends Serializable> extends Serializable{

  /**
   * 返回实体的id
   * 
   * @param <ID> a ID object.
   * @return a ID object.
   */
  public ID getIdentifier();

  /**
   * 是否是持久化对象
   * 
   * @return a boolean.
   */
  public boolean isPersisted();

  /**
   * 是否为未持久化对象
   * 
   * @return a boolean.
   */
  public boolean isTransient();
}

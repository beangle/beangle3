/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.dao.entity.LongIdEntity;
import org.beangle.commons.dao.entity.TemporalActiveEntity;

/**
 * 权限
 * 
 * @author chaostone 2005-9-26
 */
public interface Permission extends LongIdEntity, Cloneable, TemporalActiveEntity {
  /**
   * 系统资源
   * 
   * @return
   */
  public Resource getResource();

  /**
   * 获得授权对象
   * 
   * @param ao
   */
  public Role getRole();

  /**
   * 授权的操作
   * 
   * @return
   */
  public String getActions();

  /**
   * 资源过滤器
   * 
   * @return
   */
  public String getFilters();

  /**
   * 访问资源时执行的检查条件
   * 
   * @return
   */
  public String getGuards();

  /**
   * 允许访问的部分
   * 
   * @return
   */
  public String getParts();
  
  public void merge(Permission other);

  public Object clone();
}

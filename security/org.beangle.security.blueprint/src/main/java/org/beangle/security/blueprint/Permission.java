/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import java.security.Principal;

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
  public Principal getPrincipal();

  /**
   * 授权的操作
   * 
   * @return
   */
  public String getActions();

  /**
   * 访问资源时执行的检查条件
   * 
   * @return
   */
  public String getRestrictions();

  /**
   * 说明
   * 
   * @return
   */
  public String getRemark();
}

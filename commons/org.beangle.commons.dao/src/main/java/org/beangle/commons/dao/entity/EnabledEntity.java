/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.entity;

/**
 * 能够启用和禁用的实体
 * 
 * @author chaostone
 * @version $Id: StatusEntity.java Jun 25, 2011 5:05:07 PM chaostone $
 */
public interface EnabledEntity {

  /**
   * 查询是否启用
   * 
   * @return 是否启用
   */
  public boolean isEnabled();

  /**
   * 设置是否启用
   * 
   * @param enabled
   *          是否启用
   */
  public void setEnabled(boolean enabled);

}

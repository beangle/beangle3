/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 系统资源.<br>
 * 
 * @author chaostone 2008-7-28
 */
public interface Resource extends LongIdEntity {

  /** 资源的所有部分 */
  public static final String AllParts = "*";

  /** 允许所有操作 */
  public static final String AllActions = "*";

  /**
   * 资源名称
   * 
   */
  String getName();

  /**
   * 资源名称
   * 
   * @param name
   */
  void setName(String name);

  /**
   * 资源标题
   * 
   */
  String getTitle();

  /**
   * 资源标题
   * 
   * @param title
   */
  void setTitle(String title);

  /**
   * 允许的操作
   * 
   */
  String getActions();

  /**
   * 资源状态
   * 
   */
  boolean isEnabled();

  /**
   * 设置资源状态
   * 
   * @param IsActive
   */
  void setEnabled(boolean isEnabled);

  /**
   * 返回资源描述
   * 
   */
  String getRemark();

}

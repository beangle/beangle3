/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 用户自定义属性
 * 
 * @author chaostone
 * @since 2011-09-22
 */
public interface ProfileField extends LongIdEntity {

  /**
   * 名称
   */
  String getName();

  /**
   * 标题
   */
  String getTitle();

  /**
   * 值类型
   */
  DataType getType();

  /**
   * 数据源提供者
   */
  String getSource();

  /**
   * 是否为集合类型
   */
  boolean isMultiple();

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.entity;

import java.util.Date;

/**
 * 有时效性的实体
 * </p>
 * 指有具体生效时间和失效时间的实体。一般生效时间不能为空，失效时间可以为空。
 * 具体时间采用时间时间格式便于比对。
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface TemporalActiveEntity {

  /**
   * 获得生效时间
   * 
   * @return 生效时间
   */
  Date getEffectiveAt();

  /**
   * 获得失效时间
   * 
   * @return 失效时间
   */
  Date getInvalidAt();

  /**
   * 设置生效时间
   * 
   * @param effectiveAt
   */
  void setEffectiveAt(Date effectiveAt);

  /**
   * 设置失效时间
   * 
   * @param invalidAt
   */
  void setInvalidAt(Date invalidAt);

}

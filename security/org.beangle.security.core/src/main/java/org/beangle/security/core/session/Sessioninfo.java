/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Date;

import org.beangle.commons.dao.entity.StringIdEntity;

/**
 * 在线活动
 * 
 * @author chaostone
 */
public interface Sessioninfo extends StringIdEntity {
  /**
   * 用户名
   * 
   * @return
   */
  public String getUsername();

  /**
   * 用户全名
   * 
   * @return
   */
  public String getFullname();

  /**
   * 登录时间
   * 
   * @return
   */
  public Date getLoginAt();

  /**
   * 是否过期
   * 
   * @return
   */
  public boolean isExpired();

  /**
   * 在线时间
   * 
   * @return
   */
  public long getOnlineTime();

  /**
   * 备注
   * 
   * @return
   */
  public String getRemark();

  /**
   * 使之过期
   */
  public void expireNow();

  /**
   * 添加备注
   * 
   * @param added
   */
  public void addRemark(String added);

  /**
   * 查询过期时间
   * 
   * @return
   */
  public Date getExpiredAt();

  /**
   * 查询最后访问时间
   * 
   * @return
   */
  public Date getLastAccessAt();

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.dao.annotation.FlashEntity;
import org.beangle.commons.orm.pojo.LongIdObject;

/**
 * 分类会话计数状态
 * 
 * @author chaostone
 * @version $Id: CategorySessionStat.java Jun 18, 2011 2:56:08 PM chaostone $
 */
@Entity(name = "org.beangle.security.core.session.category.SessionStat")
@FlashEntity
public class SessionStat extends LongIdObject {

  private static final long serialVersionUID = 8698006403892972254L;

  /** 统计时间戳 */
  @NotNull
  private Date statAt = new Date();

  /** 用户分类 */
  @NotNull
  private String category;

  /** 最大容量 */
  private int capacity;

  /** 实际在线 */
  @Column(name = "on_line")
  private int online;

  /** 过期时间 */
  private int inactiveInterval;

  /** 单用户最大会话数 */
  private int userMaxSessions;

  public SessionStat() {
    super();
  }

  public SessionStat(String category, int capacity, int userMaxSessions) {
    super();
    this.category = category;
    this.capacity = capacity;
    this.userMaxSessions = userMaxSessions;
  }

  public boolean allocate(String sessionId) {
    if (online < capacity) {
      online++;
      return true;
    } else {
      return false;
    }
  }

  public void free(String sessionId) {
    if (online > 0) online--;
  }

  public boolean hasCapacity() {
    return online < capacity;
  }

  public boolean isFull() {
    return !hasCapacity();
  }

  public Date getStatAt() {
    return statAt;
  }

  public void setStatAt(Date statAt) {
    this.statAt = statAt;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getOnline() {
    return online;
  }

  public void setOnline(int online) {
    this.online = online;
  }

  public int getInactiveInterval() {
    return inactiveInterval;
  }

  public void setInactiveInterval(int inactiveInterval) {
    this.inactiveInterval = inactiveInterval;
  }

  public int getUserMaxSessions() {
    return userMaxSessions;
  }

  public void setUserMaxSessions(int userMaxSessions) {
    this.userMaxSessions = userMaxSessions;
  }

  public void adjust(int newAllocated) {
    this.capacity += newAllocated;
  }

}

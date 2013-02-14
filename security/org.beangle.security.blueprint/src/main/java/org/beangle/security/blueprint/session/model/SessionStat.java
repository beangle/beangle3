/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.session.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.annotation.FlashEntity;
import org.beangle.commons.entity.pojo.IntegerIdObject;

/**
 * 分类会话计数状态
 * 
 * @author chaostone
 * @version $Id: CategorySessionStat.java Jun 18, 2011 2:56:08 PM chaostone $
 */
@Entity(name = "org.beangle.security.blueprint.session.model.SessionStat")
@FlashEntity
public class SessionStat extends IntegerIdObject {

  private static final long serialVersionUID = 8698006403892972254L;

  /** 统计时间戳 */
  @NotNull
  private Date statAt = new Date();

  /** 用户分类 */
  @NotNull
  @Column(unique = true)
  private String category;

  /** 最大容量 */
  private int capacity;

  /** 实际在线 */
  @Column(name = "on_line")
  private int online;

  public SessionStat() {
    super();
  }

  public SessionStat(String category, int capacity) {
    super();
    this.category = category;
    this.capacity = capacity;
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

  public void adjust(int newAllocated) {
    this.capacity += newAllocated;
  }

}

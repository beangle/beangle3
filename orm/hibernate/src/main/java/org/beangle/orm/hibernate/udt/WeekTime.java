/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.orm.hibernate.udt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Embeddable;

import org.beangle.commons.entity.Component;
import org.beangle.commons.lang.Objects;
import org.hibernate.annotations.Type;

@Embeddable
public class WeekTime implements Component, Comparable<WeekTime>, Serializable {
  private static final long serialVersionUID = -2024647580747669114L;

  private java.sql.Date startOn;

  /** 开始时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute beginAt;

  /** 结束时间 */
  @Type(type = "org.beangle.orm.hibernate.udt.HourMinuteType")
  private HourMinute endAt;

  /** 周状态数字 */
  @Type(type = "org.beangle.orm.hibernate.udt.WeekStateType")
  private WeekState weekstate;

  public int compareTo(WeekTime other) {
    return Objects.compareBuilder().add(this.startOn, other.startOn).add(this.beginAt, other.beginAt)
        .add(this.endAt, other.endAt).toComparison();
  }

  public int miniutes() {
    return endAt.interval(beginAt);
  }

  @Override
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = prime * result + ((weekstate == null) ? 0 : weekstate.hashCode());
    result = prime * result + ((beginAt == null) ? 0 : beginAt.hashCode());
    result = prime * result + ((endAt == null) ? 0 : endAt.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof WeekTime) {
      WeekTime wt = (WeekTime) obj;
      return new Objects.EqualsBuilder().add(this.beginAt, wt.beginAt).add(this.endAt, wt.endAt)
          .add(this.weekstate, wt.weekstate).add(this.startOn, wt.startOn).isEquals();
    } else {
      return false;
    }
  }

  public WeekTime() {
    super();
  }

  public WeekTime(WeekTime other) {
    super();
    this.beginAt = other.beginAt;
    this.endAt = other.endAt;
    this.weekstate = other.weekstate;
    this.startOn = other.startOn;
  }

  // FIMXE need test
  public List<java.sql.Date> getDates() {
    List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
    LocalDate start = startOn.toLocalDate();
    for (Integer weekIndex : weekstate.getWeekList()) {
      dates.add(java.sql.Date.valueOf(start.plusWeeks(weekIndex - 1)));
    }
    return dates;
  }

  public java.sql.Date getDate(int week) {
    LocalDate start = startOn.toLocalDate();
    return java.sql.Date.valueOf(start.plusWeeks(week - 1));
  }

  // FIMXE need test
  public boolean isOverlap(WeekTime time) {
    return ((time.getWeekstate().value & this.getWeekstate().value) > 0
        && time.getStartOn().equals(this.getStartOn()) && time.getBeginAt().le(this.getEndAt())
        && time.getEndAt().ge(this.getBeginAt()));
  }

  // FIMXE need test
  public WeekDay getWeekday() {
    Calendar c = Calendar.getInstance();
    c.setTime(getStartOn());
    return WeekDay.getDayByJdkIndex(c.get(Calendar.DAY_OF_WEEK));
  }

  /**
   * 克隆时间
   */
  public Object clone() {
    WeekTime time = new WeekTime();
    time.setWeekstate(getWeekstate());
    time.setBeginAt(getBeginAt());
    time.setEndAt(getEndAt());
    time.setStartOn(getStartOn());
    return time;
  }

  public int getStartYear() {
    if (null == startOn) return 0;
    else return startOn.getYear() + 1900;
  }

  public java.sql.Date getStartOn() {
    return startOn;
  }

  public void setStartOn(java.sql.Date startOn) {
    this.startOn = startOn;
  }

  public HourMinute getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(HourMinute beginAt) {
    this.beginAt = beginAt;
  }

  public HourMinute getEndAt() {
    return endAt;
  }

  public void setEndAt(HourMinute endAt) {
    this.endAt = endAt;
  }

  public WeekState getWeekstate() {
    return weekstate;
  }

  public void setWeekstate(WeekState weekstate) {
    this.weekstate = weekstate;
  }

  /**
   * 返回该时间表示的第一天 FIXME
   *
   * @return
   */
  public java.sql.Date getFirstDay() {
    if (null != getWeekstate()) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setMinimalDaysInFirstWeek(1);
      gc.setTime(getStartOn());
      gc.add(Calendar.WEEK_OF_YEAR, getWeekstate().getWeekList().get(0) - 1);
      gc.set(Calendar.HOUR_OF_DAY, 0);
      gc.set(Calendar.MINUTE, 0);
      gc.set(Calendar.SECOND, 0);
      gc.set(Calendar.MILLISECOND, 0);
      return new java.sql.Date(gc.getTime().getTime());
    } else {
      return null;
    }
  }

  /***********************************************************************************************
   * 时间区间
   *
   * @return
   */
  public String getTimeZone() {
    if (null != getBeginAt() && null != getEndAt()) {
      return getBeginAt() + "-" + getEndAt();
    } else {
      return "";
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return "[startOn:" + getStartOn() + " beginAt:" + getBeginAt() + " endAt:" + getEndAt() + " weeks:"
        + getWeekstate() + "]";

  }

  public static LocalDate getStartOn(int year, WeekDay weekday) {
    LocalDate startDate = LocalDate.of(year, 1, 1);
    while (startDate.getDayOfWeek().getValue() != weekday.getId()) {
      startDate = startDate.plusDays(1);
    }
    return startDate;
  }

  public static WeekTime of(java.sql.Date date) {
    LocalDate ld = date.toLocalDate();
    LocalDate yearStartOn = getStartOn(ld.getYear(), WeekDay.get(ld.getDayOfWeek().getValue()));
    WeekTime weektime = new WeekTime();
    weektime.setStartOn(java.sql.Date.valueOf(yearStartOn));
    weektime.setWeekstate(WeekState.of(Weeks.between(yearStartOn, ld) + 1));
    return weektime;
  }

  /**
   * 查询指定日期是否在该时间单元中. FIXME
   *
   * @param date
   * @return
   */
  public boolean contains(java.sql.Date date) {
    WeekTime wt = of(date);
    return (wt.getStartOn().equals(this.getStartOn())
        && (wt.getWeekstate().value & this.getWeekstate().value) > 0);
  }

  public java.sql.Date getLastDay() {
    if (null != getWeekstate()) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(getStartOn());
      gc.add(Calendar.WEEK_OF_YEAR, getWeekstate().getLast() - 1);
      gc.set(Calendar.HOUR_OF_DAY, getBeginAt().value / 100);
      gc.set(Calendar.MINUTE, getBeginAt().value % 100);
      return new java.sql.Date(gc.getTime().getTime());
    }
    return null;
  }

  public void dropDay(java.sql.Date lastDay) {
    LocalDate start = startOn.toLocalDate();
    LocalDate end = lastDay.toLocalDate();
    if (end.getDayOfWeek() == start.getDayOfWeek()) {
      int weeks = Weeks.between(start, end) + 1;
      if (weeks <= 63 && weeks >= 0 && this.weekstate.isOccupied(weeks)) {
        this.weekstate = weekstate.bitxor(WeekState.of(weeks));
      }
    }
  }

  public void addDay(java.sql.Date lastDay) {
    LocalDate start = startOn.toLocalDate();
    LocalDate end = lastDay.toLocalDate();
    if (end.getDayOfWeek() == start.getDayOfWeek()) {
      int weeks = Weeks.between(start, end) + 1;
      if (weeks <= 53 && weeks >= 0 && !this.weekstate.isOccupied(weeks)) {
        this.weekstate = weekstate.bitor(WeekState.of(weeks));
      }
    }
  }
}

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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 一周内，星期名称于其标号的实体类
 */
public class WeekDay implements Serializable, Comparable<WeekDay> {
  private static final long serialVersionUID = -7003967188245072343L;
  private final int id;
  /**
   * 对应编号的名称
   */
  private final String name;

  /**
   * 对应编号的英文名称
   */
  private final String enName;

  public WeekDay(int id, String name, String enName) {
    this.id = id;
    this.name = name;
    this.enName = enName;
  }

  public String getEnName() {
    return enName;
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    if (id == 7) return 1;
    else return id + 1;
  }

  // FIXME need test
  public WeekDay previous() {
    int preDayId = this.getId() - 1;
    if (preDayId <= 0) {
      return WeekDay.Sun;
    } else {
      return WeekDay.get(preDayId);
    }
  }

  // FIXME need test
  public WeekDay next() {
    int nextDayId = this.getId() + 1;
    if (nextDayId > 7) {
      return WeekDay.Mon;
    } else {
      return WeekDay.get(nextDayId);
    }
  }

  public static final WeekDay Mon = new WeekDay(1, "星期一", "Mon");
  public static final WeekDay Tue = new WeekDay(2, "星期二", "Tue");
  public static final WeekDay Wed = new WeekDay(3, "星期三", "Wed");
  public static final WeekDay Thu = new WeekDay(4, "星期四", "Thu");
  public static final WeekDay Fri = new WeekDay(5, "星期五", "Fri");
  public static final WeekDay Sat = new WeekDay(6, "星期六", "Sat");
  public static final WeekDay Sun = new WeekDay(7, "星期日", "Sun");

  /**
   * 周一～周日
   */
  public static WeekDay[] All = new WeekDay[]{Mon, Tue, Wed, Thu, Fri, Sat, Sun};

  /**
   * 周日～周六
   */
  public static WeekDay[] All_SUNDAY_FIRST = new WeekDay[]{Sun, Mon, Tue, Wed, Thu, Fri, Sat};

  /**
   * 查找星期[1..7]
   *
   * @param weekId
   * @return
   */
  public static WeekDay get(int weekId) {
    return All[weekId - 1];
  }

  public static WeekDay[] getWeekdayArray(boolean firstDayOnSunday) {
    if (firstDayOnSunday) {
      return All_SUNDAY_FIRST;
    }
    return WeekDay.All;
  }

  /**
   * 根据Jdk Calendar API的weekday规则(周日:1,周一:2,...,周六:7)，返回WeekDay。<br>
   * 在使用的时候应该用Calendar.SUNDAY, Calendar.MONDAY，...作为参数传入，便于程序阅读
   *
   * @param jdkWeekdayIndex
   * @return
   */
  public static WeekDay getDayByJdkIndex(int jdkWeekdayIndex) {
    int id = 0;
    if (jdkWeekdayIndex == 1) id = 7;
    else id = jdkWeekdayIndex - 1;
    return get(id);
  }

  /**
   * 获得WeekDay的List，从一周的第一天到一周的第最后天排序
   *
   * @param firstDayOnSunday 星期天是否一周的第一天
   * @return
   */
  public static List<WeekDay> getWeekdayList(boolean firstDayOnSunday) {
    return Arrays.asList(getWeekdayArray(firstDayOnSunday));
  }

  public static WeekDay of(java.util.Date d) {
    return WeekDay.get(LocalDateTime.from(d.toInstant()).getDayOfWeek().getValue());
  }

  public int compareTo(WeekDay other) {
    return this.id - other.id;
  }

  public int getId() {
    return id;
  }

}

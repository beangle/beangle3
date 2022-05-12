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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

public class WeekTimes {

  /**
   * 判断两个时间是否可以合并<br>
   * 判断标准为 （weekState、weekday相等） 且 （上课节次相连 或 上课节次相交）
   * 或者节次相等则可以合并周次
   *
   * @param other
   * @return
   */
  public static boolean canMergerWith(WeekTime me, WeekTime other) {
    if (me.getStartOn().equals(other.getStartOn())) {
      if (me.getWeekstate().equals(other.getWeekstate())) {
        if ((me.getBeginAt().interval(other.getEndAt()) < 20 || (other.getBeginAt().interval(me.getEndAt()) < 20))) {
          return true;
        } else {
          return (me.getBeginAt().value <= other.getEndAt().value)
              && (other.getBeginAt().value <= me.getEndAt().value);
        }
      } else {
        return me.getBeginAt().equals(other.getBeginAt()) && me.getEndAt().equals(other.getEndAt());
      }
    } else {
      return false;
    }
  }

  /**77777
   * 将两上课时间进行合并，前提是这两上课时间可以合并
   *
   * @see #canMergerWith(WeekTime)
   * @param other
   */
  public static void mergeWith(WeekTime me, WeekTime other) {
    if (me.getWeekstate().equals(other.getWeekstate())) {
      if (other.getBeginAt().value < me.getBeginAt().value) {
        me.setBeginAt(other.getBeginAt());
      }

      if (other.getEndAt().value > me.getEndAt().value) {
        me.setEndAt(other.getEndAt());
      }
    } else {
      me.setWeekstate(new WeekState(me.getWeekstate().value | other.getWeekstate().value));
    }
  }

  /**
   * 合并相邻或者重叠的时间段<br>
   * 前提条件是待合并的
   *
   * @param tobeMerged
   * @return
   */
  public static List<WeekTime> mergeTimes(List<WeekTime> tobeMerged) {
    if(tobeMerged.isEmpty())return tobeMerged;
    Collections.sort(tobeMerged);
    List<WeekTime> mergedTimeUnits = CollectUtils.newArrayList();
    Iterator<WeekTime> activityIter = tobeMerged.iterator();
    WeekTime toMerged = (WeekTime) activityIter.next();
    mergedTimeUnits.add(toMerged);
    while (activityIter.hasNext()) {
      WeekTime unit = (WeekTime) activityIter.next();
      if (canMergerWith(toMerged, unit)) {
        mergeWith(toMerged, unit);
      } else {
        toMerged = unit;
        mergedTimeUnits.add(toMerged);
      }
    }
    return mergedTimeUnits;
  }
}

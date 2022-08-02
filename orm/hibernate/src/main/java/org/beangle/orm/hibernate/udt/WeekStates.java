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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

public final class WeekStates {

  /**
   * 输入1-8,10-12,14-18双,20-26单，之类的字符串，返回构造出来的WeekState
   *
   * @param str
   * @return
   */
  public static WeekState build(String str) {
    String newstr = Strings.replace(Strings.replace(Strings.replace(str, "，", ","), "－", "-"), "—", "-");
    String[] weekPairs = Strings.split(newstr, ",");

    List<Integer> numbers = CollectUtils.newArrayList();
    for (String weekPair : weekPairs) {
      if (Strings.contains(weekPair, "-")) {
        NumberSequence.Pattern pattern = NumberSequence.Pattern.CONTINUE;
        if (weekPair.indexOf('单') != -1) {
          pattern = NumberSequence.Pattern.ODD;
        } else if (weekPair.indexOf('双') != -1) {
          pattern = NumberSequence.Pattern.EVEN;
        }
        weekPair = weekPair.replaceAll("[^\\d-]", "");
        String startWeek = Strings.substringBefore(weekPair, "-");
        String endWeek = Strings.substringAfter(weekPair, "-");

        if (Numbers.isDigits(startWeek) && Numbers.isDigits(endWeek)) {
          Integer[] nums = NumberSequence.buildInteger(Numbers.toInt(startWeek), Numbers.toInt(endWeek),
              pattern);
          numbers.addAll(Arrays.asList(nums));
        }
      } else {
        if (Numbers.isDigits(weekPair)) {
          numbers.add(Numbers.toInt(weekPair));
        }
      }
    }
    int[] weekResult = new int[numbers.size()];
    for (int i = 0; i < numbers.size(); i++) {
      weekResult[i] = numbers.get(i);
    }
    return WeekState.of(weekResult);
  }

  /**
   * 是否在第53周与下一年重叠
   *
   * @param year
   * @return
   */
  private static boolean shareAt53(int year) {
    String lastDay = year + "-12-31";
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(java.sql.Date.valueOf(lastDay));
    return (gregorianCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY);
  }
}

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

import static org.beangle.commons.lang.Numbers.toInt;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.beangle.commons.lang.Assert;

public class HourMinute implements Serializable, Comparable<HourMinute> {
  private static final long serialVersionUID = 3782695973214593536L;

  public final short value;

  public static final HourMinute Zero = new HourMinute((short) 0);

  public HourMinute(short value) {
    super();
    this.value = value;
  }

  public int getHour() {
    return value / 100;
  }

  public int getMinute() {
    return value % 100;
  }

  public HourMinute(String str) {
    this(convert(str));
  }

  @Override
  public int compareTo(HourMinute o) {
    return this.value - o.value;
  }

  public int toMinutes() {
    String time = String.valueOf(value);
    if (value >= 6000) throw new RuntimeException("Invalid time " + time);
    while (time.length() < 4)
      time = "0" + time;
    return toInt(time.substring(0, 2)) * 60 + toInt(time.substring(2, 4));
  }

  public int interval(HourMinute other) {
    return Math.abs(this.toMinutes() - other.toMinutes());
  }

  public boolean gt(HourMinute other) {
    return this.value > other.value;
  }

  public boolean lt(HourMinute other) {
    return this.value < other.value;
  }

  public boolean le(HourMinute other) {
    return this.value <= other.value;
  }

  public boolean ge(HourMinute other) {
    return this.value >= other.value;
  }

  public String toString() {
    String time = String.valueOf(value);
    if (value >= 6000) throw new RuntimeException("Invalid time " + time);
    while (time.length() < 4)
      time = "0" + time;
    return time.substring(0, 2) + ":" + time.substring(2, 4);
  }

  private static short convert(String time) {
    int index = time.indexOf(':');
    Assert.isTrue(index == 2 && time.length() == 5, "illegal time,it should with 00:00 format");
    Assert.isTrue((toInt(time.substring(0, index)) < 60 && toInt(time.substring(index + 1, index + 3)) < 60),
        "illegal time " + time + ",it should within 60:60.");
    return (short) toInt(time.substring(0, index) + time.substring(index + 1, index + 3));
  }

  public static HourMinute of(java.util.Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    return new HourMinute(sdf.format(date));
  }

  public static HourMinute newHourMinute(String time) {
    int index = time.indexOf(':');
    Assert.isTrue(index == 2 && time.length() == 5, "illegal time,it should with 00:00 format");
    Assert.isTrue((toInt(time.substring(0, index)) < 60 && toInt(time.substring(index + 1, index + 3)) < 60),
        "illegal time " + time + ",it should within 60:60.");
    return new HourMinute((short) toInt(time.substring(0, index) + time.substring(index + 1, index + 3)));
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    return this.value == ((HourMinute) obj).value;
  }

  public short getValue() {
    return value;
  }

}

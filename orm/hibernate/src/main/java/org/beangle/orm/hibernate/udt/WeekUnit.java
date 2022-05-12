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

public class WeekUnit {

  int cycle;

  int start;

  int end;

  public WeekUnit(int cycle, int start, int end) {
    this.cycle = cycle;
    this.start = start;
    this.end = end;
  }

  public String toString() {
    return (cycle + "[" + start + "-" + end + "]");
  }

  public int getCycle() {
    return cycle;
  }

  public void setCycle(int cycle) {
    this.cycle = cycle;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

}

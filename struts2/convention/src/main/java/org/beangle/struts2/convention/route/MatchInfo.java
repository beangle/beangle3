/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.struts2.convention.route;

/**
 * action匹配信息
 * 
 * @author chaostone
 */
public class MatchInfo {

  /** last char index of matcher */
  int startIndex;

  StringBuilder reserved = new StringBuilder(0);

  public MatchInfo(int startIndex) {
    super();
    this.startIndex = startIndex;
  }

  public StringBuilder getReserved() {
    return reserved;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public String toString() {
    return reserved.toString();
  }

}

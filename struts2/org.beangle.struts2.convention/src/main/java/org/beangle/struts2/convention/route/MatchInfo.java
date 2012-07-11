/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

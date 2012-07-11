/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

/**
 * 查询分页限制
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PageLimit implements Limit {

  private int pageNo;

  private int pageSize;

  /**
   * <p>
   * Constructor for PageLimit.
   * </p>
   */
  public PageLimit() {
    super();
  }

  /**
   * <p>
   * Constructor for PageLimit.
   * </p>
   * 
   * @param pageNo
   *          a int.
   * @param pageSize
   *          a int.
   */
  public PageLimit(final int pageNo, final int pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

  /**
   * <p>
   * Getter for the field <code>pageSize</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * <p>
   * Setter for the field <code>pageSize</code>.
   * </p>
   * 
   * @param pageSize
   *          a int.
   */
  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * <p>
   * Getter for the field <code>pageNo</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getPageNo() {
    return pageNo;
  }

  /**
   * <p>
   * Setter for the field <code>pageNo</code>.
   * </p>
   * 
   * @param pageNo
   *          a int.
   */
  public void setPageNo(final int pageNo) {
    this.pageNo = pageNo;
  }

  /**
   * <p>
   * isValid.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isValid() {
    return pageNo > 0 && pageSize > 0;
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @see java.lang.Object#toString()
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return new StringBuilder().append("pageNo:").append(pageNo).append(" pageSize:").append(pageSize)
        .toString();
  }

}

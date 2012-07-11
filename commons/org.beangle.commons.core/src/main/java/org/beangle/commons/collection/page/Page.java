/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

import java.util.List;

/**
 * 分页对象
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Page<E> extends List<E> {
  /** Constant <code>DEFAULT_PAGE_NUM=1</code> */
  public static final int DEFAULT_PAGE_NUM = 1;

  /** Constant <code>DEFAULT_PAGE_SIZE=20</code> */
  public static final int DEFAULT_PAGE_SIZE = 20;

  /**
   * 第一页.
   * 
   * @return 1
   * @param <E> a E object.
   */
  public int getFirstPageNo();

  /**
   * 最大页码
   * 
   * @return a int.
   */
  public int getMaxPageNo();

  /**
   * 下一页页码
   * 
   * @return a int.
   */
  public int getNextPageNo();

  /**
   * 上一页页码
   * 
   * @return a int.
   */
  public int getPreviousPageNo();

  /**
   * 当前页码
   * 
   * @return a int.
   */
  public int getPageNo();

  /**
   * 每页大小
   * 
   * @return a int.
   */
  public int getPageSize();

  /**
   * 数据总量
   * 
   * @return a int.
   */
  public int getTotal();

  /**
   * 下一页
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> next();

  /**
   * 是否还有下一页
   * 
   * @return a boolean.
   */
  public boolean hasNext();

  /**
   * 上一页
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> previous();

  /**
   * 是否还有上一页
   * 
   * @return a boolean.
   */
  public boolean hasPrevious();

  /**
   * 调转到指定页
   * 
   * @param pageNo a int.
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> moveTo(int pageNo);

  /**
   * <p>
   * getItems.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<E> getItems();

}

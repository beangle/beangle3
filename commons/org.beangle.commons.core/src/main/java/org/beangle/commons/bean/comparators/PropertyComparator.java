/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.comparators;

import java.util.Comparator;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

/**
 * 属性比较器。<br>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyComparator<T> implements Comparator<T> {

  private String cmpWhat;

  private int index = -1;

  private boolean asc;

  // 升序情况下，null排在前面，降序情况下，null排在后面。
  private boolean nullFirst = true;

  @SuppressWarnings("rawtypes")
  private Comparator comparator;

  private StringComparator stringComparator;

  /**
   * new OrderedBeanComparator("id") or<br>
   * new OrderedBeanComparator("name desc"); new
   * OrderedBeanComparator("[0].name desc");
   * 
   * @param cmpStr a {@link java.lang.String} object.
   */
  public PropertyComparator(final String cmpStr) {
    if (Strings.isEmpty(cmpStr)) { return; }

    if (Strings.contains(cmpStr, ',')) { throw new RuntimeException(
        "PropertyComparator don't suport comma based order by." + " Use MultiPropertyComparator "); }
    cmpWhat = cmpStr.trim();
    // 处理带有[]符号的字符串
    if ('[' == cmpWhat.charAt(0)) {
      index = Numbers.toInt(Strings.substringBetween(cmpWhat, "[", "]"));
      cmpWhat = Strings.substringAfter(cmpWhat, "]");
      if (cmpWhat.length() > 0 && '.' == cmpWhat.charAt(0)) {
        cmpWhat = cmpWhat.substring(1);
      }
    }
    // 处理排序
    asc = true;
    if (Strings.contains(cmpWhat, ' ')) {
      if (Strings.contains(cmpWhat, " desc")) {
        asc = false;
      }
      cmpWhat = cmpWhat.substring(0, cmpWhat.indexOf(' '));
    }
    stringComparator = new CollatorStringComparator(asc);
  }

  /**
   * <p>
   * Constructor for PropertyComparator.
   * </p>
   * 
   * @param cmpWhat a {@link java.lang.String} object.
   * @param asc a boolean.
   */
  public PropertyComparator(final String cmpWhat, final boolean asc) {
    this(cmpWhat + " " + (asc ? "" : "desc"));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public int compare(Object arg0, Object arg1) {
    Object what0 = null;
    Object what1 = null;
    // 取出属性
    if (index > -1) {
      arg0 = ((Object[]) arg0)[index];
      arg1 = ((Object[]) arg1)[index];
      if (Strings.isEmpty(cmpWhat)) {
        what0 = arg0;
        what1 = arg1;
      }
    }
    if (Strings.isNotEmpty(cmpWhat)) {
      try {
        what0 = PropertyUtils.getProperty(arg0, cmpWhat);
      } catch (NestedNullException e) {
        what0 = null;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      try {
        what1 = PropertyUtils.getProperty(arg1, cmpWhat);
      } catch (NestedNullException e) {
        what1 = null;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    if (what0 == null && null == what1) { return 0; }

    // 进行比较
    if (null == comparator) {
      if (what0 == null && null != what1) { return asc && nullFirst ? -1 : 1; }

      if (what0 != null && null == what1) { return asc && nullFirst ? 1 : -1; }
      // 进行字符串比较
      if (what0 instanceof String || what1 instanceof String) {
        return stringComparator.compare(what0.toString(), what1.toString());
      } else {
        if (asc) {
          return ((Comparable<Object>) what0).compareTo(what1);
        } else {
          return ((Comparable<Object>) what1).compareTo(what0);
        }
      }
    } else {
      // return doCompare(comparator,what0,what1);
      return comparator.compare(what0, what1);
    }
  }

  /**
   * <p>
   * Getter for the field <code>comparator</code>.
   * </p>
   * 
   * @return a {@link java.util.Comparator} object.
   */
  @SuppressWarnings("rawtypes")
  public Comparator getComparator() {
    return comparator;
  }

  /**
   * <p>
   * Setter for the field <code>comparator</code>.
   * </p>
   * 
   * @param comparator a {@link java.util.Comparator} object.
   */
  @SuppressWarnings("rawtypes")
  public void setComparator(final Comparator comparator) {
    this.comparator = comparator;
  }

  /**
   * <p>
   * Getter for the field <code>stringComparator</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.bean.comparators.StringComparator} object.
   */
  public StringComparator getStringComparator() {
    return stringComparator;
  }

  /**
   * <p>
   * Setter for the field <code>stringComparator</code>.
   * </p>
   * 
   * @param stringComparator a {@link org.beangle.commons.bean.comparators.StringComparator} object.
   */
  public void setStringComparator(final StringComparator stringComparator) {
    this.stringComparator = stringComparator;
  }

  /**
   * <p>
   * Getter for the field <code>cmpWhat</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getCmpWhat() {
    return cmpWhat;
  }

  /**
   * <p>
   * isAsc.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isAsc() {
    return asc;
  }

  /**
   * <p>
   * isNullFirst.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isNullFirst() {
    return nullFirst;
  }

  /**
   * <p>
   * Setter for the field <code>nullFirst</code>.
   * </p>
   * 
   * @param nullFirst a boolean.
   */
  public void setNullFirst(boolean nullFirst) {
    this.nullFirst = nullFirst;
  }

}

/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.collection;

import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.lang.Strings;

/**
 * 排序
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Order {

  /** Constant <code>ORDER_STR="orderBy"</code> */
  public static final String ORDER_STR = "orderBy";

  private String property;

  private boolean ascending;

  private boolean ignoreCase;

  /**
   * <p>
   * Constructor for Order.
   * </p>
   */
  public Order() {
    super();
  }

  /**
   * <p>
   * Constructor for Order.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   * @param ascending a boolean.
   */
  public Order(String property, boolean ascending) {
    this.property = property;
    this.ascending = ascending;
  }

  /**
   * <p>
   * Constructor for Order.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   */
  public Order(String property) {
    if (Strings.contains(property, ",")) { throw new RuntimeException("user parser for multiorder"); }
    if (Strings.contains(property, " desc")) {
      this.ascending = false;
      this.property = Strings.substringBefore(property, " desc");
    } else {
      if (Strings.contains(property, " asc")) {
        this.property = Strings.substringBefore(property, " asc");
      } else {
        this.property = property;
      }
      this.ascending = true;
    }
    this.property = this.property.trim();
  }

  /**
   * <p>
   * Getter for the field <code>property</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getProperty() {
    return property;
  }

  /**
   * <p>
   * Setter for the field <code>property</code>.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   */
  public void setProperty(final String property) {
    this.property = property;
  }

  /**
   * <p>
   * isAscending.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isAscending() {
    return ascending;
  }

  /**
   * <p>
   * Setter for the field <code>ascending</code>.
   * </p>
   * 
   * @param ascending a boolean.
   */
  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }

  /**
   * <p>
   * ignoreCase.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.Order} object.
   */
  public Order ignoreCase() {
    ignoreCase = true;
    return this;
  }

  /**
   * <p>
   * asc.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.collection.Order} object.
   */
  public static Order asc(String property) {
    return new Order(property, true);
  }

  /**
   * <p>
   * desc.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.collection.Order} object.
   */
  public static Order desc(String property) {
    return new Order(property, false);
  }

  /**
   * <p>
   * toSortString.
   * </p>
   * 
   * @param orders a {@link java.util.List} object.
   * @return a {@link java.lang.String} object.
   */
  public static String toSortString(final List<Order> orders) {
    if (null == orders || orders.isEmpty()) { return ""; }
    final StringBuilder buf = new StringBuilder("order by ");
    for (final Order order : orders) {
      if (order.isAscending()) {
        buf.append(order.getProperty()).append(',');
      } else {
        buf.append(order.getProperty()).append(" desc,");
      }
    }
    return buf.substring(0, buf.length() - 1).toString();
  }

  /**
   * <p>
   * parse.
   * </p>
   * 
   * @param orderString a {@link java.lang.String} object.
   * @return a {@link java.util.List} object.
   */
  public static List<Order> parse(final String orderString) {
    if (Strings.isBlank(orderString)) {
      return new ArrayList<Order>();
    } else {
      final List<Order> orders = new ArrayList<Order>();
      final String[] orderStrs = Strings.split(orderString, ',');
      for (int i = 0; i < orderStrs.length; i++) {
        String order = orderStrs[i].trim();
        if (Strings.isBlank(order)) {
          continue;
        }
        order = order.toLowerCase().trim();
        if (order.endsWith(" desc")) {
          orders.add(new Order(orderStrs[i].substring(0, order.indexOf(" desc")), false));
        } else if (order.endsWith(" asc")) {
          orders.add(new Order(orderStrs[i].substring(0, order.indexOf(" asc")), true));
        } else {
          orders.add(new Order(orderStrs[i], true));
        }
      }
      return orders;
    }
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    if (ignoreCase) {
      return "lower(" + getProperty() + ") " + (ascending ? "asc" : "desc");
    } else {
      return getProperty() + " " + (ascending ? "asc" : "desc");
    }
  }

}

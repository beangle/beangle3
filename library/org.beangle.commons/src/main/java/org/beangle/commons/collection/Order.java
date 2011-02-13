/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 排序
 */
public class Order {

	public static final String ORDER_STR = "orderBy";

	private String property;

	private boolean ascending;

	private boolean ignoreCase;

	public Order() {
		super();
	}

	public Order(String property, boolean ascending) {
		this.property = property;
		this.ascending = ascending;
	}

	public Order(String property) {
		if (StringUtils.contains(property, ",")) { throw new RuntimeException("user parser for multiorder"); }
		if (StringUtils.contains(property, " desc")) {
			this.ascending = false;
			this.property = StringUtils.substringBefore(property, " desc");
		} else {
			if (StringUtils.contains(property, " asc")) {
				this.property = StringUtils.substringBefore(property, " asc");
			} else {
				this.property = property;
			}
			this.ascending = true;
		}
		this.property = this.property.trim();
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public Order ignoreCase() {
		ignoreCase = true;
		return this;
	}

	public static Order asc(String property) {
		return new Order(property, true);
	}

	public static Order desc(String property) {
		return new Order(property, false);
	}

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

	public static List<Order> parse(final String orderString) {
		if (StringUtils.isBlank(orderString)) {
			return new ArrayList<Order>();
		} else {
			final List<Order> orders = new ArrayList<Order>();
			final String[] orderStrs = StringUtils.split(orderString, ",");
			for (int i = 0; i < orderStrs.length; i++) {
				String order = orderStrs[i].trim();
				if (StringUtils.isBlank(order)) {
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

	public String toString() {
		if (ignoreCase) {
			return "lower(" + getProperty() + ") " + (ascending ? "asc" : "desc");
		} else {
			return getProperty() + " " + (ascending ? "asc" : "desc");
		}
	}

}

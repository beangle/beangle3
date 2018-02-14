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
package org.beangle.commons.dao.query.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.lang.Strings;

/**
 * 查询条件 使用例子如下
 * <p>
 * <blockquote>
 *
 * <pre>
 *      new Condition(&quot;std.id=?&quot;,new Long(2));
 *      或者 Condition(&quot;std.id=:std_id&quot;,new Long(2));
 *      ?绑定单值.命名参数允许绑定多值.但是只能由字母,数组和下划线组成
 *      一组condition只能采取上面一种形式
 * </pre>
 *
 * </blockquote>
 * <p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class Condition {

  private final String content;

  private final List<Object> params = new ArrayList<Object>(1);

  /**
   * <p>
   * Constructor for Condition.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   */
  public Condition(final String content) {
    this.content = content;
  }

  /**
   * <p>
   * Constructor for Condition.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   */
  public Condition(final String content, Object param1) {
    this.content = content;
    params.add(param1);
  }

  /**
   * <p>
   * Constructor for Condition.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   * @param param2 a {@link java.lang.Object} object.
   */
  public Condition(final String content, Object param1, Object param2) {
    this(content, param1, param2, null);
  }

  /**
   * <p>
   * Constructor for Condition.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   * @param param2 a {@link java.lang.Object} object.
   * @param param3 a {@link java.lang.Object} object.
   */
  public Condition(final String content, Object param1, Object param2, Object param3) {
    this.content = content;
    if (null != param1) params.add(param1);
    if (null != param2) params.add(param2);
    if (null != param3) params.add(param3);
  }

  /**
   * <p>
   * Getter for the field <code>content</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getContent() {
    return content;
  }

  /**
   * <p>
   * Getter for the field <code>params</code>.
   * </p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<Object> getParams() {
    return params;
  }

  /**
   * <p>
   * isNamed.
   * </p>
   *
   * @return a boolean.
   */
  public boolean isNamed() {
    return !Strings.contains(content, "?");
  }

  /**
   * 得到查询条件中所有的命名参数.
   *
   * @return a {@link java.util.List} object.
   */
  public List<String> getParamNames() {
    if (!Strings.contains(content, ":")) { return Collections.emptyList(); }
    final List<String> params = new ArrayList<String>();
    int index = 0;
    do {
      final int colonIndex = content.indexOf(':', index);
      if (-1 == colonIndex) {
        break;
      }
      index = colonIndex + 1;
      while (index < content.length()) {
        final char c = content.charAt(index);
        if (isValidIdentifierStarter(c)) {
          index++;
        } else {
          break;
        }
      }
      final String paramName = content.substring(colonIndex + 1, index);
      if (!params.contains(paramName)) {
        params.add(paramName);
      }
    } while (index < content.length());
    return params;
  }

  private static boolean isValidIdentifierStarter(final char ch) {
    return (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || (ch == '_') || ('0' <= ch && ch <= '9'));
  }

  /**
   * <p>
   * param.
   * </p>
   *
   * @param value a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public Condition param(final Object value) {
    params.add(value);
    return this;
  }

  /**
   * <p>
   * params.
   * </p>
   *
   * @param values a {@link java.util.List} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public Condition params(final List<?> values) {
    params.clear();
    params.addAll(values);
    return this;
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
    final StringBuilder str = new StringBuilder(content).append(" ");
    for (final Object value : params) {
      str.append(value);
    }
    return str.toString();
  }

  /** {@inheritDoc} */
  public boolean equals(final Object obj) {
    if (null == getContent() || !(obj instanceof Condition)) {
      return false;
    } else {
      return getContent().equals(((Condition) obj).getContent());
    }
  }

  /**
   * <p>
   * hashCode.
   * </p>
   *
   * @return a int.
   */
  public int hashCode() {
    if (null == content) {
      return 0;
    } else {
      return content.hashCode();
    }
  }

  // 以下是几个方便的方法
  /**
   * <p>
   * eq.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.Number} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition eq(final String content, final Number value) {
    return new Condition(content + " = " + value);
  }

  /**
   * <p>
   * eq.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition eq(final String content, final String value) {
    int attrIndex = content.lastIndexOf('.');
    String paramName = ":" + (
          attrIndex == -1 ?
              content + System.currentTimeMillis()
              : content.substring(attrIndex + 1) + System.currentTimeMillis()
          );
    return new Condition(content + " = " + paramName, value);

  }

  /**
   * <p>
   * le.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.Number} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition le(final String content, final Number value) {
    return new Condition(content + " <= " + value);
  }

  /**
   * <p>
   * ge.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.Number} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition ge(final String content, final Number value) {
    return new Condition(content + " >= " + value);
  }

  /**
   * <p>
   * ne.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.Number} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition ne(final String content, final Number value) {
    return new Condition(content + " <> " + value);
  }

  /**
   * <p>
   * like.
   * </p>
   *
   * @param content a {@link java.lang.String} object.
   * @param value a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.Condition} object.
   */
  public static Condition like(final String content, final String value) {
    int attrIndex = content.lastIndexOf('.');
    String paramName = ":" + (
          attrIndex == -1 ?
              content + System.currentTimeMillis()
              : content.substring(attrIndex + 1) + System.currentTimeMillis()
          );
    return new Condition(content + " like " + paramName, "%" + value + "%");
  }

}

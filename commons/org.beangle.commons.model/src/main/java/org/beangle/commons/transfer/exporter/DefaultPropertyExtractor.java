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
package org.beangle.commons.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.expr.EvaluationException;
import org.beangle.commons.expr.ExpressionEvaluator;
import org.beangle.commons.text.i18n.TextResource;

/**
 * 缺省和简单的属性提取类
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultPropertyExtractor implements PropertyExtractor {

  protected TextResource textResource = null;

  //FIXME expressionEvaluator
  protected ExpressionEvaluator expressionEvaluator = null;

  protected Set<String> errorProperties = CollectUtils.newHashSet();

  /**
   * <p>
   * Constructor for DefaultPropertyExtractor.
   * </p>
   */
  public DefaultPropertyExtractor() {
    super();
  }

  /**
   * <p>
   * Constructor for DefaultPropertyExtractor.
   * </p>
   * 
   * @param textResource a {@link org.beangle.commons.i18n.TextResource} object.
   */
  public DefaultPropertyExtractor(TextResource textResource) {
    setTextResource(textResource);
  }

  /**
   * <p>
   * extract.
   * </p>
   * 
   * @param target a {@link java.lang.Object} object.
   * @param property a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   * @throws java.lang.Exception if any.
   */
  protected Object extract(Object target, String property) throws Exception {
    if (errorProperties.contains(property)) return null;
    Object value = null;
    try {
      value = expressionEvaluator.eval(property, target);
    } catch (EvaluationException e) {
      return null;
    }
    if (value instanceof Boolean) {
      if (null == textResource) {
        return value;
      } else {
        if (Boolean.TRUE.equals(value)) return getText("common.yes", "Y");
        else return getText("common.no", "N");
      }
    } else {
      return value;
    }
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    return extract(target, property);
  }

  /**
   * <p>
   * getPropertyIn.
   * </p>
   * 
   * @param collection a {@link java.util.Collection} object.
   * @param property a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   * @throws java.lang.Exception if any.
   */
  public String getPropertyIn(Collection<?> collection, String property) throws Exception {
    StringBuilder sb = new StringBuilder();
    if (null != collection) {
      for (Iterator<?> iter = collection.iterator(); iter.hasNext();) {
        Object one = iter.next();
        sb.append(extract(one, property)).append(",");
      }
    }
    // 删除逗号
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  protected String getText(String key) {
    return textResource.getText(key, key);
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param defaultVal a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  protected String getText(String key, String defaultVal) {
    return textResource.getText(key, defaultVal);
  }

  /**
   * <p>
   * Getter for the field <code>textResource</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.i18n.TextResource} object.
   */
  public TextResource getTextResource() {
    return textResource;
  }

  public void setTextResource(TextResource textResource) {
    this.textResource = textResource;
  }

  public ExpressionEvaluator getExpressionEvaluator() {
    return expressionEvaluator;
  }

  public void setExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

}

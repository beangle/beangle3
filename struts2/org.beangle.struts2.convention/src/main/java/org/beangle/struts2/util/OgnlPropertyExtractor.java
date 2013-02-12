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
package org.beangle.struts2.util;

import org.beangle.commons.script.EvaluationException;
import org.beangle.commons.script.ExpressionEvaluator;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;

/**
 * Property Extractor by Ognl.
 * 
 * @author chaostone
 * @version $Id: $
 * @since 3.2.0
 */
public class OgnlPropertyExtractor extends DefaultPropertyExtractor {

  protected ExpressionEvaluator expressionEvaluator = new OgnlExpressionEvaluator();

  public OgnlPropertyExtractor() {
    super();
  }

  /**
   * <p>
   * Constructor for OgnlPropertyExtractor.
   * </p>
   */
  public OgnlPropertyExtractor(TextResource textResource) {
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

  public ExpressionEvaluator getExpressionEvaluator() {
    return expressionEvaluator;
  }

  public void setExpressionEvaluator(ExpressionEvaluator expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

}

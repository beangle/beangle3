/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.exporter;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.TransferMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * SimpleEntityExporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SimpleEntityExporter extends SimpleItemExporter {

  private static final Logger logger = LoggerFactory.getLogger(SimpleEntityExporter.class);
  /**
   * 导入属性
   */
  protected String[] attrs;

  /**
   * 属性提取器
   */
  protected PropertyExtractor propertyExtractor;

  /** {@inheritDoc} */
  @Override
  protected boolean beforeExport() {
    if (null == attrs) {
      String keys = context.get("keys", String.class);
      if (Strings.isNotBlank(keys)) {
        attrs = Strings.split(keys, ",");
      }
    }
    if (null == propertyExtractor) {
      propertyExtractor = context.get("extractor", PropertyExtractor.class);
    }
    if (null == attrs || null == propertyExtractor) {
      logger.debug("attrs or propertyExtractor is null,transfer data as array.");
    }
    return super.beforeExport();
  }

  /**
   * 转换单个实体
   */
  public void transferItem() {
    if (null == attrs) {
      super.transferItem();
      return;
    }
    Object[] values = new Object[attrs.length];
    for (int i = 0; i < values.length; i++) {
      try {
        values[i] = propertyExtractor.getPropertyValue(getCurrent(), attrs[i]);
      } catch (Exception e) {
        transferResult.addFailure(TransferMessage.ERROR_ATTRS_EXPORT, "occur in get property :" + attrs[i]
            + " and exception:" + e.getMessage());
      }
    }
    writer.write(values);
  }

  /**
   * <p>
   * Getter for the field <code>propertyExtractor</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.exporter.PropertyExtractor} object.
   */
  public PropertyExtractor getPropertyExtractor() {
    return propertyExtractor;
  }

  /**
   * <p>
   * Setter for the field <code>propertyExtractor</code>.
   * </p>
   * 
   * @param propertyExporter a {@link org.beangle.commons.transfer.exporter.PropertyExtractor}
   *          object.
   */
  public void setPropertyExtractor(PropertyExtractor propertyExporter) {
    this.propertyExtractor = propertyExporter;
  }

  /**
   * <p>
   * Getter for the field <code>attrs</code>.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] getAttrs() {
    return attrs;
  }

  /**
   * <p>
   * Setter for the field <code>attrs</code>.
   * </p>
   * 
   * @param attrs an array of {@link java.lang.String} objects.
   */
  public void setAttrs(String[] attrs) {
    this.attrs = attrs;
  }
}

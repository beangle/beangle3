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

import java.util.List;

import org.beangle.commons.transfer.TransferMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多个实体集合导出器。
 * <p>
 * 每个实体的数据List组成一个新的List，作为导出的items.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MultiEntityExporter extends AbstractItemExporter {
  private static final Logger logger = LoggerFactory.getLogger(MultiEntityExporter.class);
  /**
   * 属性提取器
   */
  protected PropertyExtractor propertyExtractor;

  protected List<Metadata> metadatas = null;

  public static class Metadata {
    String dateName;
    String[] attrs;
    String[] titles;

    public Metadata(String dateName, String[] attrs, String[] titles) {
      super();
      this.dateName = dateName;
      this.attrs = attrs;
      this.titles = titles;
    }
  }

  /**
   * <p>
   * beforeExport.
   * </p>
   * 
   * @return a boolean.
   */
  @SuppressWarnings("unchecked")
  protected boolean beforeExport() {
    if (null == propertyExtractor) {
      propertyExtractor = context.get("extractor", PropertyExtractor.class);
    }
    if (null == metadatas) {
      metadatas = (List<Metadata>) context.get("metadatas");
    }
    if (null == metadatas || null == propertyExtractor) {
      logger.error("without metadatas or propertyExtractor,exporter stopped!");
      return false;
    }
    return true;
  }

  /**
   * <p>
   * transferItem.
   * </p>
   */
  public void transferItem() {
    Metadata metadata = metadatas.get(index);
    List<?> values = (List<?>) ((List<?>) context.get("items")).get(index);
    getWriter().writeTitle(metadata.dateName, metadata.titles);
    Object[] propValues = new Object[metadata.attrs.length];
    for (Object item : values) {
      for (int i = 0; i < propValues.length; i++) {
        try {
          propValues[i] = propertyExtractor.getPropertyValue(item, metadata.attrs[i]);
        } catch (Exception e) {
          transferResult.addFailure(TransferMessage.ERROR_ATTRS_EXPORT, "occur in get property :"
              + metadata.attrs[i] + " and exception:" + e.getMessage());
        }
      }
      writer.write(propValues);
    }
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
}

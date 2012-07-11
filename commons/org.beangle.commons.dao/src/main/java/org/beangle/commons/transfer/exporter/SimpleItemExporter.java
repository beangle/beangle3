/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.exporter;

import org.beangle.commons.lang.Strings;

/**
 * <p>
 * SimpleItemExporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SimpleItemExporter extends AbstractItemExporter {
  /** 导出属性对应的标题 */
  protected String[] titles;

  /**
   * <p>
   * Getter for the field <code>titles</code>.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] getTitles() {
    return titles;
  }

  /**
   * <p>
   * Setter for the field <code>titles</code>.
   * </p>
   * 
   * @param titles an array of {@link java.lang.String} objects.
   */
  public void setTitles(String[] titles) {
    this.titles = titles;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean beforeExport() {
    if (null == titles) {
      String contextTitle = context.get("titles", String.class);
      if (null != contextTitle) {
        titles = Strings.split(contextTitle, ",");
      }
    }
    if (null == titles || titles.length == 0) return false;
    writer.writeTitle(null, titles);
    return true;
  }

}

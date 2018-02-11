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

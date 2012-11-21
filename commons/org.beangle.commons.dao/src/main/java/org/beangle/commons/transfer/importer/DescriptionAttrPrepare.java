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
package org.beangle.commons.transfer.importer;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.transfer.io.ItemReader;

/**
 * <p>
 * DescriptionAttrPrepare class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DescriptionAttrPrepare implements ImportPrepare {

  /** {@inheritDoc} */
  public void prepare(Importer importer) {
    ItemReader reader = (ItemReader) importer.getReader();
    String[] descs = reader.readDescription();
    String[] attrs = reader.readTitle();
    Map<String, String> descriptions = CollectUtils.newHashMap();
    for (int i = 0; i < attrs.length && i < descs.length; i++) {
      descriptions.put(attrs[i], descs[i]);
    }
    ((AbstractItemImporter) importer).setAttrs(attrs);
    ((AbstractItemImporter) importer).setDescriptions(descriptions);
  }

}

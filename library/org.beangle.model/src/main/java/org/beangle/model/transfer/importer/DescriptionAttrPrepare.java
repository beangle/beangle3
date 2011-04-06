/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.importer;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.transfer.io.ItemReader;

public class DescriptionAttrPrepare implements ImportPrepare {

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

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.importer;

import java.util.Set;

import org.beangle.dao.metadata.Populator;
import org.beangle.transfer.io.ItemReader;

public interface EntityImporter extends Importer {

	public Set<String> getForeignerKeys();

	public void addForeignedKeys(String foreignerKey);

	public void setPopulator(Populator populator);

	public ItemReader getReader();

}

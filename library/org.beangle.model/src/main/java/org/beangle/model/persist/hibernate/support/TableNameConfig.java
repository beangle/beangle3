/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

import java.net.URL;
import java.util.List;

public interface TableNameConfig {

	public void addConfig(URL url);

	public String getSchema(String packageName);

	public String getPrefix(String packageName);

	public List<TableNamePattern> getPatterns();

}

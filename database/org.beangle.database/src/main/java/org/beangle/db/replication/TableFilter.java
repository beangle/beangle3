/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import java.util.Collection;

public interface TableFilter {

	public Collection<String> filter(Collection<String> tables);

}

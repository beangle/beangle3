/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

public class GivenOrderFilter implements TableFilter {

	List<String> givenTables = CollectUtils.newArrayList();

	private GivenOrderFilter(List<String> givenTables) {
		super();
		this.givenTables = CollectUtils.newArrayList(givenTables);
	}

	public Collection<String> filter(Collection<String> tables) {
		return null;
	}

}

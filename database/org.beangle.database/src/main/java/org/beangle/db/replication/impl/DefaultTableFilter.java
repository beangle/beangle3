/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.replication.TableFilter;
import org.springframework.util.StringUtils;

public class DefaultTableFilter implements TableFilter {

	List<String> excludes = CollectUtils.newArrayList();

	List<String> includes = CollectUtils.newArrayList();

	public List<String> getExcludes() {
		return excludes;
	}

	public Collection<String> filter(Collection<String> tables) {
		List<String> newTables = CollectUtils.newArrayList();
		for (String tableName : tables) {
			boolean passed = includes.isEmpty();
			for (String pattern : includes) {
				if (StringUtils.startsWithIgnoreCase(tableName, pattern)) {
					passed = true;
				}
			}
			if (passed) {
				for (String pattern : excludes) {
					if (tableName.toLowerCase().contains(pattern.toLowerCase())) {
						passed = false;
					}
				}
			}
			if (passed) {
				newTables.add(tableName);
			}
		}
		return newTables;
	}

	public void addExclude(String table) {
		excludes.add(table);
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void addInclude(String table) {
		includes.add(table);
	}

}

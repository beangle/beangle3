/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;


public interface TableNameConfig {

	public String classToTableName(String className);
	
	public String collectionToTableName(String className,String tableName,String collectionName);
	
	public String getPrefix(String packageName);

}

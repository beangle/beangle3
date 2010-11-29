/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit;

/**
 * 包
 * 
 * @author chaostone
 */
public interface Resource {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public String getSummary();

	public void setSummary(String summary);

	public String getDescription();

	public void setDescription(String description);

	public int getSize();

	public void setSize(int size);

	public boolean isNewerThen(Resource other);

	public String getPackageName();

	public void setPackageName(String packageName);

	public Repository getRepository();

}

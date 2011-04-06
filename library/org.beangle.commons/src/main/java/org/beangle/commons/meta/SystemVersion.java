/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.meta;

/**
 * System version
 * 
 * @author chaostone
 */
public interface SystemVersion {

	public String getName();

	public void setName(String name);

	public String getVendor();

	public void setVendor(String vendor);

	public String getVersion();

	public void setVersion(String version);

	public int getMajorVersion();

	public void setMajorVersion(int majorVersion);

	public int getMinorVersion();

	public void setMinorVersion(int minorVersion);

}

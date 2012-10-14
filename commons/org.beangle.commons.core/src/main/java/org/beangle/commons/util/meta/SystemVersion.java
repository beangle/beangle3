/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.meta;

/**
 * System version
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface SystemVersion {

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  String getName();

  /**
   * <p>
   * setName.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   */
  void setName(String name);

  /**
   * <p>
   * getVendor.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  String getVendor();

  /**
   * <p>
   * setVendor.
   * </p>
   * 
   * @param vendor a {@link java.lang.String} object.
   */
  void setVendor(String vendor);

  /**
   * <p>
   * getVersion.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  String getVersion();

  /**
   * <p>
   * setVersion.
   * </p>
   * 
   * @param version a {@link java.lang.String} object.
   */
  void setVersion(String version);

  /**
   * <p>
   * getMajorVersion.
   * </p>
   * 
   * @return a int.
   */
  int getMajorVersion();

  /**
   * <p>
   * setMajorVersion.
   * </p>
   * 
   * @param majorVersion a int.
   */
  void setMajorVersion(int majorVersion);

  /**
   * <p>
   * getMinorVersion.
   * </p>
   * 
   * @return a int.
   */
  int getMinorVersion();

  /**
   * <p>
   * setMinorVersion.
   * </p>
   * 
   * @param minorVersion a int.
   */
  void setMinorVersion(int minorVersion);

}

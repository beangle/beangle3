/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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

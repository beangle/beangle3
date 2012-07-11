/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.meta;

/**
 * <p>
 * SystemVersionBean class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SystemVersionBean implements SystemVersion {

  private String name;

  private String vendor;

  private String version;

  private int majorVersion;

  private int minorVersion;

  /**
   * <p>
   * Getter for the field <code>name</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return name;
  }

  /** {@inheritDoc} */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * <p>
   * Getter for the field <code>vendor</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getVendor() {
    return vendor;
  }

  /** {@inheritDoc} */
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  /**
   * <p>
   * Getter for the field <code>version</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getVersion() {
    return version;
  }

  /** {@inheritDoc} */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * <p>
   * Getter for the field <code>majorVersion</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getMajorVersion() {
    return majorVersion;
  }

  /** {@inheritDoc} */
  public void setMajorVersion(int majorVersion) {
    this.majorVersion = majorVersion;
  }

  /**
   * <p>
   * Getter for the field <code>minorVersion</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getMinorVersion() {
    return minorVersion;
  }

  /** {@inheritDoc} */
  public void setMinorVersion(int minorVersion) {
    this.minorVersion = minorVersion;
  }
}

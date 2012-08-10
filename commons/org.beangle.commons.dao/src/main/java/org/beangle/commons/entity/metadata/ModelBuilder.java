/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

/**
 * <p>
 * ModelBuilder interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface ModelBuilder {

  /**
   * <p>
   * build.
   * </p>
   */
  public void build();

  /**
   * <p>
   * getModel.
   * </p>
   * 
   * @return a {@link org.beangle.commons.entity.metadata.Model} object.
   */
  public Model getModel();

}

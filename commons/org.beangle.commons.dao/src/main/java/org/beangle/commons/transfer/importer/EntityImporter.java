/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer;

import java.util.Set;

import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.transfer.io.ItemReader;

/**
 * <p>
 * EntityImporter interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EntityImporter extends Importer {

  /**
   * <p>
   * getForeignerKeys.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  public Set<String> getForeignerKeys();

  /**
   * <p>
   * addForeignedKeys.
   * </p>
   * 
   * @param foreignerKey a {@link java.lang.String} object.
   */
  public void addForeignedKeys(String foreignerKey);

  /**
   * <p>
   * setPopulator.
   * </p>
   * 
   * @param populator a {@link org.beangle.commons.entity.metadata.Populator} object.
   */
  public void setPopulator(Populator populator);

  /**
   * <p>
   * getReader.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.io.ItemReader} object.
   */
  public ItemReader getReader();

}

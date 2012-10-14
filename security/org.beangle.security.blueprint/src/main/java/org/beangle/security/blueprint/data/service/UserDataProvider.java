/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service;

import java.util.List;

import org.beangle.security.blueprint.data.ProfileField;

/**
 * @author chaostone
 * @version $Id: UserDataProvider.java Nov 9, 2010 7:18:38 PM chaostone $
 */
public interface UserDataProvider {

  /**
   * extract data from source
   * 
   * @param <T>
   * @param field
   * @param source
   * @param keys
   */
  public <T> List<T> getData(ProfileField field, String source,Object... keys);

  /**
   * provider's unique name
   * 
   */
  public String getName();
}

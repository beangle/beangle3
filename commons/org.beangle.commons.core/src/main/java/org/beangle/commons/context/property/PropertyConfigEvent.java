/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.property;

import java.util.EventObject;

/**
 * <p>
 * PropertyConfigEvent class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyConfigEvent extends EventObject {

  private static final long serialVersionUID = 6125300646790912291L;

  /**
   * <p>
   * Constructor for PropertyConfigEvent.
   * </p>
   * 
   * @param config a {@link org.beangle.commons.context.property.PropertyConfig} object.
   */
  public PropertyConfigEvent(PropertyConfig config) {
    super(config);
  }

  /** {@inheritDoc} */
  @Override
  public PropertyConfig getSource() {
    return (PropertyConfig) super.getSource();
  }

}

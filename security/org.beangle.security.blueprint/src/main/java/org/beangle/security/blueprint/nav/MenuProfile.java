/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.nav;

import java.util.List;

import org.beangle.commons.entity.pojo.LongIdEntity;
import org.beangle.security.blueprint.Role;

/**
 * Menu profile.
 * 
 * @author chaostone
 * @since 2.0
 */
public interface MenuProfile extends LongIdEntity {

  String getName();

  List<Menu> getMenus();

  Role getRole();

  /**
   * 资源状态
   */
  boolean isEnabled();

}

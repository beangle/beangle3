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

  void setName(String name);

  List<Menu> getMenus();

  void setMenus(List<Menu> menus);

  Role getRole();

  void setRole(Role role);

  /**
   * 资源状态
   */
  boolean isEnabled();

  /**
   * 设置资源状态
   * 
   * @param isEnabled
   */
  void setEnabled(boolean isEnabled);
}

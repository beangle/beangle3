/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.nav;

import java.util.List;

import org.beangle.commons.entity.pojo.LongIdEntity;
import org.beangle.security.blueprint.Role;

public interface MenuProfile extends LongIdEntity {

  public String getName();

  public void setName(String name);

  public List<Menu> getMenus();

  public void setMenus(List<Menu> menus);

  public Role getRole();

  public void setRole(Role role);

  /**
   * 资源状态
   * 
   * @return
   */
  public boolean isEnabled();

  /**
   * 设置资源状态
   * 
   * @param IsActive
   * @return
   */
  public void setEnabled(boolean isEnabled);
}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.entity.pojo.LongIdTimeEntity;

/**
 * @author chaostone
 * @version $Id: Member.java Nov 2, 2010 6:24:48 PM chaostone $
 */
public interface Member extends LongIdTimeEntity {

  /**
   * get role
   */
  Role getRole();

  /**
   * set role
   * 
   * @param role
   */
  void setRole(Role role);

  /**
   * Get User
   */
  User getUser();

  /**
   * 是否为成员
   */
  boolean isMember();

  /**
   * 是否是为管理者
   */
  boolean isManager();

  /**
   * 是否能授权
   */
  boolean isGranter();

  /**
   * membership
   */
  boolean is(Ship ship);

  /**
   * 成员关系
   * 
   * @author chaostone
   */
  enum Ship {
    /**
     * just role member
     */
    MEMBER,

    /**
     * manage role perperties and permissions
     */
    MANAGER,

    /**
     * Can grant/revoke role to/from member
     */
    GRANTER;

  }

}

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
     * 成员关系可以等价于读权限
     */
    MEMBER,
    /**
     * Can grant/revoke role to/from member
     */
    GRANTER,
    /**
     * manage role perperties and permissions
     * 对组可管理意为<br>
     * <ul>
     * <li>建立下级组
     * <li>移动下级组顺序
     * <li>动态属性
     * <li>功能权限
     * <li>直接成员
     * </ul>
     * 不能改变组的
     * <ul>
     * <li>删除组
     * <li>重命名.
     * </ul>
     * 只要拥有上级组的管理权限，才能变更这些，这些称之为写权限(admin)。
     * 拥有某组的管理权限，不意味拥有下级组的管理权限。新建组情况自动授予该组的其他管理者管理权限。
     */
    MANAGER;
  }

}

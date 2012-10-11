/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdTimeObject;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

/**
 * 系统用户
 * 记录账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.User")
public class UserBean extends LongIdTimeObject implements User {
  private static final long serialVersionUID = -3625902334772342380L;

  /** 名称 */
  @Size(max = 40)
  @NotNull
  @Column(unique = true)
  protected String name;

  /** 用户姓名 */
  @NotNull
  @Size(max = 50)
  private String fullname;

  /** 用户密文 */
  @Size(max = 100)
  @NotNull
  private String password;

  /** 用户联系email */
  @NotNull
  @Size(max = 100)
  private String mail;

  /** 对应角色 */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Member> members = CollectUtils.newHashSet();

  /** 创建人 */
  @ManyToOne(fetch = FetchType.LAZY)
  private User creator;

  /**
   * 账户生效日期
   */
  @NotNull
  protected Date effectiveAt;

  /**
   * 账户失效日期
   */
  protected Date invalidAt;

  /**
   * 密码失效日期
   */
  protected Date passwordExpiredAt;

  /** 是否启用 */
  @NotNull
  protected boolean enabled;

  /** 备注 */
  @Size(max = 200)
  protected String remark;

  public UserBean() {
    super();
  }

  public UserBean(Long id) {
    setId(id);
  }

  /**
   * @return user's name
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public Set<Member> getMembers() {
    return members;
  }

  public List<Role> getRoles() {
    List<Role> roles = CollectUtils.newArrayList();
    for (Member member : members) {
      if (member.isMember()) roles.add(member.getRole());
    }
    Set<Role> allRoles = CollectUtils.newHashSet();
    for (Role g : roles) {
      while (null != g && !allRoles.contains(g)) {
        allRoles.add(g);
        g = g.getParent();
      }
    }
    roles.clear();
    roles.addAll(allRoles);
    Collections.sort(roles);
    return roles;
  }

  public void setMembers(Set<Member> members) {
    this.members = members;
  }

  /**
   * 是否账户过期
   */
  public boolean isAccountExpired() {
    return EntityUtils.isExpired(this);
  }

  /**
   * 是否密码过期
   */
  public boolean isPasswordExpired() {
    return (null != passwordExpiredAt && new Date(System.currentTimeMillis()).after(passwordExpiredAt));
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Date getEffectiveAt() {
    return effectiveAt;
  }

  public void setEffectiveAt(Date effectAt) {
    this.effectiveAt = effectAt;
  }

  public Date getInvalidAt() {
    return invalidAt;
  }

  public void setInvalidAt(Date invalidAt) {
    this.invalidAt = invalidAt;
  }

  public Date getPasswordExpiredAt() {
    return passwordExpiredAt;
  }

  public void setPasswordExpiredAt(Date passwordExpiredAt) {
    this.passwordExpiredAt = passwordExpiredAt;
  }

  public String toString() {
    return new ToStringBuilder(this).append("id", this.id).append("password", this.password)
        .append("name", this.getName()).toString();
  }

}

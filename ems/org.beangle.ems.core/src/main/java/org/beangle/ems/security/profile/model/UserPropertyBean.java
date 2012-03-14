/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.profile.UserProfile;
import org.beangle.ems.security.profile.UserProperty;
import org.beangle.ems.security.profile.PropertyMeta;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.profile.UserProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPropertyBean extends LongIdObject implements UserProperty {
	private static final long serialVersionUID = 1L;

	private String value;

	private PropertyMeta meta;

	private UserProfile profile;

	public UserPropertyBean() {
		super();
	}

	public UserPropertyBean(UserProfileBean profile, PropertyMeta meta, String value) {
		super();
		this.profile = profile;
		this.meta = meta;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PropertyMeta getMeta() {
		return meta;
	}

	public void setMeta(PropertyMeta meta) {
		this.meta = meta;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

}

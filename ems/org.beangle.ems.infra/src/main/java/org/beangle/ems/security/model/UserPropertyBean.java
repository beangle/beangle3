/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.ems.meta.PropertyMeta;
import org.beangle.ems.security.UserProfile;
import org.beangle.ems.security.UserProperty;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.UserProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPropertyBean extends LongIdObject implements UserProperty {
	private static final long serialVersionUID = 1L;

	private String value;

	private PropertyMeta meta;

	private UserProfile profile;

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

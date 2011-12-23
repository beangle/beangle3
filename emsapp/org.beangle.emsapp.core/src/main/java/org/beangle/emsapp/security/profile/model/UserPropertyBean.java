/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.profile.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.emsapp.security.profile.UserProfile;
import org.beangle.emsapp.security.profile.UserProperty;
import org.beangle.emsapp.security.profile.UserPropertyMeta;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.emsapp.security.profile.UserProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPropertyBean extends LongIdObject implements UserProperty {
	private static final long serialVersionUID = 1L;

	private String value;

	private UserPropertyMeta meta;

	private UserProfile profile;

	public UserPropertyBean() {
		super();
	}

	public UserPropertyBean(UserProfileBean profile, UserPropertyMeta meta, String value) {
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

	public UserPropertyMeta getMeta() {
		return meta;
	}

	public void setMeta(UserPropertyMeta meta) {
		this.meta = meta;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

}

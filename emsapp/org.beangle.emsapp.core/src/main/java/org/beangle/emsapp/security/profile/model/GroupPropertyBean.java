/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.profile.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.emsapp.security.profile.GroupProfile;
import org.beangle.emsapp.security.profile.GroupProperty;
import org.beangle.emsapp.security.profile.GroupPropertyMeta;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.emsapp.security.profile.GroupProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupPropertyBean extends LongIdObject implements GroupProperty {
	private static final long serialVersionUID = 1L;

	private String value;

	private GroupPropertyMeta meta;

	private GroupProfile profile;

	public GroupPropertyBean() {
		super();
	}

	public GroupPropertyBean(GroupProfileBean profile, GroupPropertyMeta meta, String value) {
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

	public GroupPropertyMeta getMeta() {
		return meta;
	}

	public void setMeta(GroupPropertyMeta meta) {
		this.meta = meta;
	}

	public GroupProfile getProfile() {
		return profile;
	}

	public void setProfile(GroupProfile profile) {
		this.profile = profile;
	}

}

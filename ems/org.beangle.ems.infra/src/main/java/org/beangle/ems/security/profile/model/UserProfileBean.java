/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.profile.Property;
import org.beangle.ems.security.profile.PropertyMeta;
import org.beangle.ems.security.profile.UserProfile;
import org.beangle.ems.security.profile.UserProperty;
import org.beangle.ems.security.profile.UserPropertyMeta;
import org.beangle.model.pojo.LongIdObject;

/**
 * 用户配置
 * 
 * @author chaostone
 * @version $Id: UserProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.ems.security.profile.UserProfile")
public class UserProfileBean extends LongIdObject implements UserProfile {

	private static final long serialVersionUID = -9047586316477373803L;
	/** 用户 */
	private User user;
	/**
	 * 用户自定义属性
	 */
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	protected List<UserProperty> properties = CollectUtils.newArrayList();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<UserProperty> properties) {
		this.properties = properties;
	}

	public UserProperty getProperty(UserPropertyMeta meta) {
		if (null == properties || properties.isEmpty()) {
			return null;
		} else {
			for (UserProperty p : properties) {
				if (p.getMeta().equals(meta)) return p;
			}
		}
		return null;
	}

	public Property getProperty(PropertyMeta meta) {
		return getProperty((UserPropertyMeta) meta);
	}

	public void setProperty(UserPropertyMeta meta, String text) {
		UserProperty property = getProperty(meta);
		if (null == property) {
			property = new UserPropertyBean(this, meta, text);
			properties.add(property);
		} else {
			property.setValue(text);
		}
	}

	// public UserProperty getField(String paramName) {
	// for (final UserProperty param : fields) {
	// if (param.getName().equals(paramName)) { return param; }
	// }
	// return null;
	// }

}

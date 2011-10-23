/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.PropertyMeta;
import org.beangle.ems.security.User;
import org.beangle.ems.security.UserProfile;
import org.beangle.ems.security.UserProperty;
import org.beangle.model.pojo.LongIdObject;

/**
 * 用户配置
 * 
 * @author chaostone
 * @version $Id: UserProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.ems.security.UserProfile")
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

	public String getProperty(PropertyMeta meta) {
		if (null == properties || properties.isEmpty()) {
			return null;
		} else {
			for (UserProperty p : properties) {
				if (p.getMeta().equals(meta)) { return p.getValue(); }
			}
		}
		return null;
	}

	public void setProperty(PropertyMeta property, String text) {
		properties.put(property.getId(), text);
	}

	// public UserProperty getField(String paramName) {
	// for (final UserProperty param : fields) {
	// if (param.getName().equals(paramName)) { return param; }
	// }
	// return null;
	// }

}

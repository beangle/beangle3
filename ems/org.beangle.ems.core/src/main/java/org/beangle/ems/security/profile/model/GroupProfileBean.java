/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.beangle.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.profile.GroupProfile;
import org.beangle.ems.security.profile.GroupProperty;
import org.beangle.ems.security.profile.GroupPropertyMeta;
import org.beangle.ems.security.profile.Property;
import org.beangle.ems.security.profile.PropertyMeta;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户组配置
 * 
 * @author chaostone
 * @version $Id: GroupProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.ems.security.profile.GroupProfile")
public class GroupProfileBean extends LongIdObject implements GroupProfile {

	private static final long serialVersionUID = -9047586316477373803L;
	/** 用户组 */
	private Group group;
	/**
	 * 用户组自定义属性
	 */
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	protected List<GroupProperty> properties = CollectUtils.newArrayList();

	/** 访问限制 */
	@ManyToMany
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	protected Set<Restriction> restrictions = CollectUtils.newHashSet();

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<GroupProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<GroupProperty> properties) {
		this.properties = properties;
	}

	public GroupProperty getProperty(GroupPropertyMeta meta) {
		if (null == properties || properties.isEmpty()) {
			return null;
		} else {
			for (GroupProperty p : properties) {
				if (p.getMeta().equals(meta)) return p;
			}
		}
		return null;
	}

	public Property getProperty(PropertyMeta meta) {
		return getProperty((GroupPropertyMeta) meta);
	}

	public void setProperty(GroupPropertyMeta meta, String text) {
		GroupProperty property = getProperty(meta);
		if (null == property) {
			property = new GroupPropertyBean(this, meta, text);
			properties.add(property);
		} else {
			property.setValue(text);
		}
	}

	// public GroupProperty getField(String paramName) {
	// for (final GroupProperty param : fields) {
	// if (param.getName().equals(paramName)) { return param; }
	// }
	// return null;
	// }

}

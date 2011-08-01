/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "org.beangle.ems.security.restrict.RestrictField")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RestrictFieldBean extends LongIdObject implements RestrictField {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	@Size(max = 20)
	private String keyName;

	@Size(max = 100)
	private String propertyNames;

	@NotNull
	@Size(max = 100)
	private String type;

	@NotNull
	private String remark;

	@Size(max = 200)
	private String source;

	@NotNull
	private boolean multiple;

	@ManyToMany(mappedBy = "fields")
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<RestrictEntity> entities = CollectUtils.newHashSet();

	public RestrictFieldBean() {
		super();
	}

	public RestrictFieldBean(String name, String type, String source) {
		super();
		this.name = name;
		this.type = type;
		this.source = source;
		this.multiple = true;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(String propertyNames) {
		this.propertyNames = propertyNames;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<RestrictEntity> getEntities() {
		return entities;
	}

	public void setEntities(Set<RestrictEntity> objects) {
		this.entities = objects;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

}

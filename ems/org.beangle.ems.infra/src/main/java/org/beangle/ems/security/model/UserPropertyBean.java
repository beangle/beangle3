/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	/** 名称 */
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	/** 关键字名称 */
	@Size(max = 20)
	private String keyName;

	/** 其它属性名(逗号隔开) */
	@Size(max = 100)
	private String propertyNames;

	/** 类型 */
	@NotNull
	@Size(max = 100)
	private String valueType;

	/** 备注 */
	@NotNull
	private String remark;

	/** 数据提供描述 */
	@Size(max = 200)
	private String source;

	/** 能够提供多值 */
	@NotNull
	private boolean multiple;

	public UserPropertyBean() {
		super();
	}

	public UserPropertyBean(String name, String type, String source) {
		super();
		this.name = name;
		this.valueType = type;
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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}

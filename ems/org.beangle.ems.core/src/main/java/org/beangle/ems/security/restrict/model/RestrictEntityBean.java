/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制实体
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.restrict.RestrictEntity")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RestrictEntityBean extends LongIdObject implements RestrictEntity {

	private static final long serialVersionUID = -5761007041977213647L;

	/** 名称 */
	private String name;

	/** 类型 */
	private String type;

	/** 备注 */
	private String remark;

	public RestrictEntityBean() {
		super();
	}

	public RestrictEntityBean(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public RestrictEntityBean(String name, Class<?> type) {
		super();
		this.name = name;
		this.type = type.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

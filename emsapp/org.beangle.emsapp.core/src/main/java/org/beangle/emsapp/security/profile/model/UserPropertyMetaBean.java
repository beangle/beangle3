/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.profile.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.emsapp.security.profile.UserPropertyMeta;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.emsapp.security.profile.UserPropertyMeta")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPropertyMetaBean extends PropertyMetaBean implements UserPropertyMeta {
	private static final long serialVersionUID = 1L;

	public UserPropertyMetaBean() {
		super();
	}

	public UserPropertyMetaBean(Long id, String name, String type, String source) {
		super(id);
		this.name = name;
		this.valueType = type;
		this.source = source;
		this.multiple = true;
	}

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 登记系统使用的基础代码
 * </p>
 * 这些代码的名称、英文名称和全称类名
 * 
 * @version $Id: Dictionary.java May 4, 2011 7:29:38 PM chaostone $
 * @author chaostone
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodeMeta extends LongIdObject {

	private static final long serialVersionUID = -2272793754309992189L;

	/** 中文名称 */
	@Column(unique = true)
	@NotNull
	@Size(max = 50)
	private String name;

	/** 代码名称 */
	@NotNull
	@Size(max = 100)
	private String engName;

	/** 类名 */
	@NotNull
	@Size(max = 100)
	private String className;

	/**
	 * 代码名称
	 * 
	 * @return 名称(民族、政治面貌等)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置代码名称
	 * 
	 * @param name
	 *            新的代码名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 代码英文名
	 * Nation、Country等
	 * 
	 * @return 代码英文名
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * 设置新的英文名
	 * 
	 * @param engName
	 *            新的英文名
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * 查询代码的类名
	 * 
	 * @return 代码的类路径全名(例如com.ekingstar.eams.basecode.nation.Country)
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 设置新的类名
	 * 
	 * @param className
	 *            类路径全称
	 */
	public void setClassName(String className) {
		this.className = className;
	}
}

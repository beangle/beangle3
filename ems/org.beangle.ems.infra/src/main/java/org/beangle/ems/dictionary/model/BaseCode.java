/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.model.pojo.TemporalActiveEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 基础代码的基类
 * </p>
 * 很对基础代码数据成员结构相似，仅在数据库中表名和列名 不一样，带都含有这些基类中规定的数据类型，所以把这种结构相似性抽出来，
 * 节省代码的编制量.每个子类代码仍要有自己的类型定义和数据库映射定义. 基类和数据库表没有映射关系，仅仅是数据抽象.
 * 
 * @author chaostone
 * @version $Id: BaseCode.java May 4, 2011 7:28:27 PM chaostone $
 */
@SuppressWarnings("rawtypes")
@MappedSuperclass
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public abstract class BaseCode<T extends BaseCode> extends LongIdTimeObject implements Comparable<T>,
		TemporalActiveEntity {

	private static final long serialVersionUID = 5728157880502841506L;

	/**
	 * 基础代码的代码关键字
	 */
	@Column(unique = true)
	@NotNull
	@Size(max = 32)
	protected String code;

	/**
	 * 代码中文名称
	 */
	@NotNull
	@Size(max = 100)
	protected String name;

	/**
	 * 代码英文名称
	 */
	@Size(max = 100)
	protected String engName;

	/**
	 * 生效时间
	 */
	@NotNull
	protected Date effectOn;

	/**
	 * 失效时间
	 */
	protected Date invalidOn;

	public BaseCode() {
	}

	public BaseCode(Long id) {
		this.id = id;
	}

	public void genIdFromCode() {
		setId(Long.valueOf(getCode()));
	}

	/**
	 * 查询基础代码是否具有扩展属性，一般供子类使用。
	 * 
	 * @return
	 */
	public boolean hasExtPros() {
		Field[] fields = getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!(Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers()))) { return true; }
		}
		return false;
	}

	/**
	 * 获得代码
	 * 
	 * @return 代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 *            代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获得名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获得英文名
	 * 
	 * @return 英文名
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * 设置英文名
	 * 
	 * @param engName
	 *            英文名
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * 获得生效时间
	 * 
	 * @return 生效时间
	 */
	public Date getEffectOn() {
		return effectOn;
	}

	/**
	 * 设置生效时间
	 * 
	 * @param effectOn
	 *            生效时间
	 */
	public void setEffectOn(Date effectOn) {
		this.effectOn = effectOn;
	}

	/**
	 * 获得失效时间
	 * 
	 * @return 失效时间
	 */
	public Date getInvalidOn() {
		return invalidOn;
	}

	/**
	 * 设置失效时间
	 * 
	 * @param invalidOn
	 *            失效时间
	 */
	public void setInvalidOn(Date invalidOn) {
		this.invalidOn = invalidOn;
	}

	public int compareTo(T arg0) {
		T other = (T) arg0;
		return this.getCode().compareTo(other.getCode());
	}

	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("id", this.id)
				.append("code", this.code).append("engName", this.engName).toString();
	}
}

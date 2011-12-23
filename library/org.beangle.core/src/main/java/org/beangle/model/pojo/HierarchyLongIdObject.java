/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.lang.StrUtils;

/**
 * @author chaostone
 * @version $Id: LongIdHierarchyObject.java Jul 29, 2011 1:18:17 AM chaostone $
 */
@MappedSuperclass
public abstract class HierarchyLongIdObject<T> extends LongIdObject implements
		HierarchyEntity<T,Long>, Comparable<T> {
	private static final long serialVersionUID = -968320812584144969L;
	
	/** 代码 */
	@Size(max = 30)
	@NotNull
	protected String code;

	public int getDepth() {
		return (null == getParent()) ? 1 : getParentNode().getDepth() + 1;
	}

	public String getIndexno() {
		String indexno = StringUtils.substringAfterLast(code, ".");
		if (StringUtils.isEmpty(indexno)) {
			indexno = code;
		}
		return indexno;
	}

	public void generateCode(String indexno) {
		if (null == getParent()) {
			this.code = indexno;
		} else {
			this.code = StrUtils.concat(getParentNode().getCode(), ".", indexno);
		}
	}

	public void generateCode() {
		if (null != getParent()) {
			this.code = StrUtils.concat(getParentNode().getCode(), ".", getIndexno());
		}
	}

	protected HierarchyLongIdObject<?> getParentNode() {
		return (HierarchyLongIdObject<?>) getParent();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 不同级的菜单按照他们固有的级联顺序排序.
	 */
	public int compareTo(T other) {
		return getCode().compareTo(((HierarchyLongIdObject<?>) other).getCode());
	}

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

@Entity(name = "org.beangle.security.blueprint.Menu")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuBean extends LongIdObject implements Menu {

	private static final long serialVersionUID = 3864556621041443066L;
	@NotNull
	@Size(max = 32)
	@Column(unique = true)
	private String code;

	@NotNull
	private int indexno;

	@NotNull
	@Size(max = 100)
	private String title;

	@NotNull
	@Size(max = 100)
	private String engTitle;

	private String entry;

	private String remark;

	@ManyToMany
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Resource> resources = CollectUtils.newHashSet();

	@NotNull
	private boolean enabled = true;

	@NotNull
	@Index(name = "idx_menu_profile")
	private MenuProfile profile;

	@Index(name = "idx_menu_parent")
	private Menu parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	@OrderBy("indexno")
	private List<Menu> children;

	/**
	 * 不同级的菜单按照他们固有的级联顺序排序.
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Menu other) {
		return getCode().compareTo(other.getCode());
	}

	public void generateCode() {
		this.code = (null != parent ? parent.getCode() : "")
				+ StringUtils.leftPad(String.valueOf(indexno), 2, '0');
	}

	public int getIndexno() {
		return indexno;
	}

	public void setIndexno(int indexno) {
		this.indexno = indexno;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEngTitle() {
		return engTitle;
	}

	public void setEngTitle(String engTitle) {
		this.engTitle = engTitle;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public MenuProfile getProfile() {
		return profile;
	}

	public void setProfile(MenuProfile profile) {
		this.profile = profile;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getDescription() {
		return StrUtils.concat("[", code, "]", title);
	}

	@Override
	public String toString() {
		return getDescription();
	}

}

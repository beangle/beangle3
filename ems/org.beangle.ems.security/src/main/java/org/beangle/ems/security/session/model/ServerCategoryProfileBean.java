/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.Category;
import org.beangle.model.pojo.LongIdObject;

/**
 * 服务器分类用户上限配额和运行情况.
 * @author chaostone
 * @version $Id: ServerCategoryProfileBean.java Jun 13, 2011 8:42:20 AM chaostone $
 */
@Entity
public class ServerCategoryProfileBean extends LongIdObject {

	private static final long serialVersionUID = 7399635823589633909L;

	@NotNull
	private String serverName;
	
	@NotNull
	protected Category category;

	@NotNull
	protected int quota;

	@NotNull
	private int used;

	@NotNull
	private Date updatedAt;
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}

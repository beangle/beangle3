/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session;

import java.sql.Timestamp;
import java.util.Date;

import org.beangle.model.pojo.LongIdEntity;
import org.beangle.security.blueprint.Category;

/**
 * 登录和退出日志
 * 
 * @author chaostone
 */
public interface SessionActivity extends LongIdEntity {

	public void calcOnlineTime();

	public String getSessionid();

	public void setSessionid(String sessionId);

	public String getName();

	public void setName(String name);

	public String getFullname();

	public void setFullname(String fullname);

	public String getHost();

	public void setHost(String host);

	public Date getLoginAt();

	public void setLoginAt(Date loginAt);

	public Date getLastAccessAt();

	public void setLastAccessAt(Date lastAccessAt);

	public Long getOnlineTime();

	public void setOnlineTime(Long sessionTime);

	public Category getCategory();

	public void setCategory(Category category);

	public Timestamp getLogoutAt();

	public void setLogoutAt(Timestamp logoutAt);

	public String getRemark();

	public void setRemark(String remark);

	public String getOs();

	public void setOs(String os);

	public String getAgent();

	public void setAgent(String agent);
}

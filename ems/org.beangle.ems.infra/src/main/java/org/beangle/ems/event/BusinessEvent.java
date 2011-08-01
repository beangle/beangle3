/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

/**
 * 业务事件
 * 
 * @author chaostone
 * @version $Id: BusinessEvent.java Jun 22, 2011 8:47:39 AM chaostone $
 */
public class BusinessEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3105001733284410829L;

	public BusinessEvent(Object source) {
		super(source);
	}

	protected Date issueAt=new Date();

	protected String resource;

	protected String description;

	protected String detail;
	
	public Date getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(Date issueAt) {
		this.issueAt = issueAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}

/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.log.BusinessLogDetail;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 业务日志实现
 * 
 * @author chaostone
 * @version $Id: BusinessLogBean.java Jun 27, 2011 7:09:58 PM chaostone $
 */
@Entity(name = "org.beangle.ems.log.BusinessLog")
public class BusinessLogBean extends LongIdObject implements BusinessLog {
	private static final long serialVersionUID = 4059776203680197111L;

	/** 操作用户 */
	@NotNull
	@Size(max = 50)
	private String operator;

	/** 操作内容 */
	@NotNull
	@Size(max = 200)
	private String operation;

	/** 操作资源 */
	@NotNull
	@Size(max = 100)
	@Column(name = "resrc")
	private String resource;

	/** 操作资源 */
	@NotNull
	@Size(max = 200)
	private String entry;

	/** 操作时间 */
	@NotNull
	private Date operateAt;

	/** 操作IP */
	@Size(max = 100)
	private String ip;

	/** 操作客户端代理 */
	@Size(max = 100)
	private String agent;

	@Cascade(CascadeType.ALL)
	private BusinessLogDetail detail;

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Date getOperateAt() {
		return operateAt;
	}

	public void setOperateAt(Date operateAt) {
		this.operateAt = operateAt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BusinessLogDetail getDetail() {
		return detail;
	}

	public void setDetail(BusinessLogDetail detail) {
		this.detail = detail;
	}
}

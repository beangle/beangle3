/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.log.BusinessLogDetail;
import org.beangle.model.pojo.LongIdObject;

/**
 * 业务日志明细
 * 
 * @author chaostone
 * @version $Id: BusinessLogDetailBean.java Aug 1, 2011 3:16:29 PM chaostone $
 */
@Entity(name = "org.beangle.ems.log.BusinessLogDetail")
public class BusinessLogDetailBean extends LongIdObject implements BusinessLogDetail {

	private static final long serialVersionUID = 8792899149257213752L;

	/** 操作参数 */
	@Lob
	private String conent;

	/** 操作日志 */
	private BusinessLog log;

	public BusinessLogDetailBean() {
		super();
	}

	public BusinessLogDetailBean(BusinessLog log, String conent) {
		super();
		this.conent = conent;
		this.log = log;
	}

	public String getConent() {
		return conent;
	}

	public void setConent(String conent) {
		this.conent = conent;
	}

	public BusinessLog getLog() {
		return log;
	}

	public void setLog(BusinessLog log) {
		this.log = log;
	}

}

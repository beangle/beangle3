/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.User;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.web.session.model.SessioninfoLogBean;


/**
 * 用户登陆退出的会话管理
 * 
 * @author chaostone
 */
public class SessioninfoLogAction extends SecurityActionSupport {

	/**
	 * 显示用户某时间段的登陆记录
	 */
	public String search() {
		OqlBuilder<SessioninfoLogBean> query = OqlBuilder.from(SessioninfoLogBean.class, "sessioninfoLog");
		addConditions(query);
		String orderBy = get("orderBy");
		if (null == orderBy) {
			orderBy = "sessioninfoLog.loginAt desc";
		}
		query.limit(getPageLimit()).orderBy(orderBy);
		put("sessioninfoLogs", entityDao.search(query));
		return forward();
	}

	private void addTimeCondition(OqlBuilder<SessioninfoLogBean> query) {
		String stime = get("startTime");
		String etime = get("endTime");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate = null, edate = null;
		if (StringUtils.isNotBlank(stime)) {
			try {
				sdate = df.parse(stime);
				// 截至日期增加一天
				if (StringUtils.isNotBlank(etime)) {
					edate = df.parse(etime);
					Calendar gc = new GregorianCalendar();
					gc.setTime(edate);
					gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + 1);
					edate = gc.getTime();
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		if (null != sdate && null == edate) {
			query.where("sessioninfoLog.loginAt >=:sdate", sdate);
		} else if (null != sdate && null != edate) {
			query.where("sessioninfoLog.loginAt >=:sdate and sessioninfoLog.loginAt <:edate", sdate, edate);
		} else if (null == sdate && null != edate) {
			query.where("sessioninfoLog.loginAt <:edate", edate);
		}
	}

	/**
	 * 显示登陆次数
	 */
	public String loginCountStat() {
		OqlBuilder<SessioninfoLogBean> query = OqlBuilder.from(SessioninfoLogBean.class, "sessioninfoLog");
		addConditions(query);
		query.select("sessioninfoLog.username,sessioninfoLog.fullname,count(*)")
				.groupBy("sessioninfoLog.username,sessioninfoLog.fullname").orderBy(get("orderBy"))
				.limit(getPageLimit());
		put("loginCountStats", entityDao.search(query));
		return forward();
	}

	private void addConditions(OqlBuilder<SessioninfoLogBean> query) {
		populateConditions(query);
		addTimeCondition(query);
		String groupName = get("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			query.where("exists(select u.id from " + User.class.getName() + " u join u.groups as gm "
					+ "where u.name=sessioninfoLog.username and gm.group.name like :groupName )", "%"
					+ groupName + "%");
		}
	}

	/**
	 * 显示用户组某时间段的登陆记录
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String timeIntervalStat() {
		OqlBuilder<SessioninfoLogBean> query = OqlBuilder.from(SessioninfoLogBean.class, "sessioninfoLog");
		addConditions(query);
		query.select("hour(sessioninfoLog.loginAt),count(*)").groupBy("hour(sessioninfoLog.loginAt)");
		List rs = entityDao.search(query);
		Collections.sort(rs, new PropertyComparator<Object[]>("[0]"));
		put("logonStats", rs);
		return forward();
	}
}

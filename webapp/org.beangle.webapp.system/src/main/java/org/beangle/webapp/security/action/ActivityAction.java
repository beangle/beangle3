/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.session.SessionActivity;

/**
 * 用户登陆退出的会话管理
 * 
 * @author chaostone
 */
public class ActivityAction extends SecurityActionSupport {

	protected void indexSetting() {
		put("categories", entityDao.getAll(UserCategory.class));
	}

	/**
	 * 显示用户某时间段的登陆记录
	 */
	public String search() {
		OqlBuilder<SessionActivity> query = OqlBuilder.from(SessionActivity.class,
				"sessionActivity");
		addConditions(query);
		String orderBy = get("orderBy");
		if (null == orderBy) {
			orderBy = "sessionActivity.loginAt desc";
		}
		query.limit(getPageLimit()).orderBy(orderBy);
		put("sessionActivitys", entityDao.search(query));
		return forward();
	}

	private void addTimeCondition(OqlBuilder<SessionActivity> query) {
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
			query.where("sessionActivity.loginAt >=:sdate", sdate);
		} else if (null != sdate && null != edate) {
			query.where("sessionActivity.loginAt >=:sdate and sessionActivity.loginAt <:edate",
					sdate, edate);
		} else if (null == sdate && null != edate) {
			query.where("sessionActivity.loginAt <:edate", edate);
		}
	}

	/**
	 * 显示用户组某时间段的登陆记录
	 */
	public String loginCountStat() {
		OqlBuilder<SessionActivity> query = OqlBuilder.from(SessionActivity.class,
				"sessionActivity");
		addConditions(query);
		query.select("sessionActivity.name,sessionActivity.fullname,count(*)").groupBy(
				"sessionActivity.name,sessionActivity.fullname").orderBy(get("orderBy")).limit(
				getPageLimit());

		put("loginCountStats", entityDao.search(query));
		return forward();
	}

	private void addConditions(OqlBuilder<SessionActivity> query) {
		populateConditions(query);
		addTimeCondition(query);
		String groupName = get("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			query.where("exists(select u.id from User u join u.groups as group "
					+ "where u.name=sessionActivity.name and group.name like :groupName )", "%"
					+ groupName + "%");
		}
	}

	/**
	 * 显示登陆次数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String timeIntervalStat() {
		OqlBuilder<SessionActivity> query = OqlBuilder.from(SessionActivity.class,
				"sessionActivity");
		addConditions(query);
		query.select("hour(sessionActivity.loginAt),count(*)").groupBy(
				"hour(sessionActivity.loginAt)");
		List rs = entityDao.search(query);
		Collections.sort(rs, new PropertyComparator<Object[]>("[0]"));
		put("logonStats", rs);
		return forward();
	}
}

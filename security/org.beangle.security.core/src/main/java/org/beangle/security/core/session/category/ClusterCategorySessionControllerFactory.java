/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.beangle.model.persist.impl.BaseServiceImpl;
import org.springframework.beans.factory.InitializingBean;

/**
 * 集群分类会话控制器
 * 
 * @author chaostone
 * @version $Id: ClusterCategorySessionControllerFactory.java Jun 20, 2011 9:35:14 PM chaostone $
 */
public abstract class ClusterCategorySessionControllerFactory extends BaseServiceImpl implements
		CategorySessionControllerFactory, InitializingBean {

	protected ClusterSessionStatMonitor monitor;

	// 默认汇报的间隔为15秒
	protected int reportInterval = 15;

	protected boolean reportSameTime = true;

	public final CategorySessionController getInstance(Object category) {
		CategorySessionController controller = doGetInstance(category);
		monitor.addController(controller);
		return controller;
	}

	protected abstract CategorySessionController doGetInstance(Object category);

	public void afterPropertiesSet() throws Exception {
		monitor = new ClusterSessionStatMonitor(entityDao);
		Calendar calendar = Calendar.getInstance();
		if (reportSameTime) {
			calendar.roll(Calendar.MINUTE, 1);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
		new Timer("Beangle Session Stat", true).schedule(new MonitorDaemon(this), calendar.getTime(),
				1000 * reportInterval);
	}

	public String getServerName() {
		return ManagementFactory.getRuntimeMXBean().getName();
	}

	public int getReportInterval() {
		return reportInterval;
	}

	public void setReportInterval(int reportInterval) {
		this.reportInterval = reportInterval;
	}

	public boolean isReportSameTime() {
		return reportSameTime;
	}

	public void setReportSameTime(boolean reportSameTime) {
		this.reportSameTime = reportSameTime;
	}

}

class MonitorDaemon extends TimerTask {
	ClusterCategorySessionControllerFactory factory;
	int cnt = 0;

	public MonitorDaemon(ClusterCategorySessionControllerFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void run() {
		cnt++;
		cnt %= 7;
		if (cnt == 0) {
			factory.monitor.gc(factory);
		}
		factory.monitor.report();
	}

}

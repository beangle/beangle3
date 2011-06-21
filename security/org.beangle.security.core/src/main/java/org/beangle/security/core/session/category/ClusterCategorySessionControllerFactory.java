/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.lang.management.ManagementFactory;
import java.util.Date;
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

	public final CategorySessionController getInstance(Object category) {
		CategorySessionController controller = doGetInstance(category);
		monitor.addController(controller);
		return controller;
	}

	protected abstract CategorySessionController doGetInstance(Object category);

	public void afterPropertiesSet() throws Exception {
		monitor = new ClusterSessionStatMonitor(entityDao);
		new Timer("Beangle Session Stat", true).schedule(new MonitorDaemon(this), new Date(), 1000 * 10);
	}

	protected String getServerName() {
		return ManagementFactory.getRuntimeMXBean().getName();
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
			factory.monitor.gc();
		}
		factory.monitor.report();
	}

}

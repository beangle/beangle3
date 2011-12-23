/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.rule.engine.impl;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.rule.Context;
import org.beangle.emsapp.rule.engine.RuleExecutor;

/**
 * 组合规则执行者
 * 
 * @author chaostone
 */
public class CompositeExecutor implements RuleExecutor {

	private List<RuleExecutor> executors = CollectUtils.newArrayList();

	/** 是否在单个规则失败后停止 默认为否 */
	private boolean stopWhenFail = false;

	public boolean execute(Context context) {
		boolean result = true;
		for (final RuleExecutor executor : executors) {
			result &= executor.execute(context);
			if (stopWhenFail && !result) { return result; }
		}
		return result;
	}

	public void add(RuleExecutor executor) {
		executors.add(executor);
	}

	public List<RuleExecutor> getExecutors() {
		return executors;
	}

	public void setExecutors(List<RuleExecutor> executors) {
		this.executors = executors;
	}

	public boolean isStopWhenFail() {
		return stopWhenFail;
	}

	public void setStopWhenFail(boolean stopWhenFailure) {
		this.stopWhenFail = stopWhenFailure;
	}

}

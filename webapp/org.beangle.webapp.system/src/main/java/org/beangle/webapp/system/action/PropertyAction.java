/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.system.action;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.config.property.ConfigFactory;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.BaseAction;
import org.beangle.webapp.system.model.PropertyConfig;

public class PropertyAction extends BaseAction implements NoParameters {

	private ConfigFactory configFactory;

	public String index() {
		OqlBuilder<PropertyConfig> builder = OqlBuilder.from(PropertyConfig.class, "config");
		builder.orderBy("config.name");
		List<PropertyConfig> configs = entityDao.search(builder);
		put("propertyConfigs", configs);
		Set<String> staticNames = configFactory.getConfig().getNames();
		for (PropertyConfig config : configs) {
			staticNames.remove(config.getName());
		}
		put("config", configFactory.getConfig());
		put("staticNames", staticNames);
		return forward();
	}

	public String save() {
		List<PropertyConfig> configs = entityDao.getAll(PropertyConfig.class);
		Set<String> names = CollectUtils.newHashSet();
		for (PropertyConfig config : configs) {
			populate(config, "config" + config.getId());
			names.add(config.getName());
		}
		entityDao.saveOrUpdate(configs);

		String msg = "info.save.success";
		PropertyConfig newConfig = populate(PropertyConfig.class, "configNew");
		if (StringUtils.isNotBlank(newConfig.getName())
				&& StringUtils.isNotBlank(newConfig.getValue())
				&& !names.contains(newConfig.getName())) {
			entityDao.saveOrUpdate(newConfig);
		}
		configFactory.reload();
		configFactory.multicast();
		return redirect("index", msg);
	}

	public String remove() {
		PropertyConfig config = entityDao.get(PropertyConfig.class, getLong("config.id"));
		if (null != config) entityDao.remove(config);
		return redirect("index", "info.save.success");
	}

	public void setConfigFactory(ConfigFactory configFactory) {
		this.configFactory = configFactory;
	}

}

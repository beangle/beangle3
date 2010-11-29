/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.context;

import java.io.InputStream;
import java.util.Properties;

import org.beangle.model.entity.EntityContext;
import org.beangle.model.entity.Model;
import org.beangle.model.entity.ModelBuilder;
import org.beangle.model.entity.Populator;

public class DefaultModelBuilder implements ModelBuilder {

	private static final String POPULATOR_PRO = "model.populatorClass";

	private static final String CONTEXT_PRO = "model.contextClass";

	/**
	 * 初始化默认对象
	 */
	public void build() {
		try {
			Properties props = new Properties();
			InputStream is = AbstractEntityContext.class
					.getResourceAsStream("/org/beangle/model/entity/model-default.properties");
			if (null != is) {
				props.load(is);
			}
			is = AbstractEntityContext.class.getResourceAsStream("/model.properties");
			if (null != is) {
				props.load(is);
			}
			if (null == Model.getContext()) {
				Class<?> contextClass = Class.forName((String) props.get(CONTEXT_PRO));
				Model.setContext((EntityContext) contextClass.newInstance());
			}
			if (null == Model.getPopulator()) {
				Class<?> populatorClass = Class.forName((String) props.get(POPULATOR_PRO));
				Model.setPopulator((Populator) populatorClass.newInstance());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Model getModel() {
		return Model.getInstance();
	}

}
